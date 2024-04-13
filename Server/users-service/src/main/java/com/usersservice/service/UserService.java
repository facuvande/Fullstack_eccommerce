package com.usersservice.service;

import com.usersservice.dto.LoginDTO;
import com.usersservice.dto.UserDTO;
import com.usersservice.dto.UserResponseDTO;
import com.usersservice.model.Role;
import com.usersservice.model.User;
import com.usersservice.repository.ICartAPI;
import com.usersservice.repository.IRolRepository;
import com.usersservice.repository.IUserRepository;
import com.usersservice.security.JwtTokenGenerator;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Service
public class UserService implements IUserService{
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private IRolRepository roleRepository;
    private JwtTokenGenerator jwtTokenGenerator;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ICartAPI iCartAPI;

    public UserService(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, IRolRepository roleRepository, JwtTokenGenerator jwtTokenGenerator, IUserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.userRepository = userRepository;
    }

    @Override
    @CircuitBreaker(name="carts-service", fallbackMethod = "fallbackCreateCart")
    @Retry(name="carts-service")
    public ResponseEntity<?> createUser(UserDTO userDTO, HttpServletResponse response) {

        // Verificar si existe el usuario con ese email
        if(this.existsByEmail(userDTO.getEmail())){
            return new ResponseEntity<>(Map.of("message","Ya existe un usuario registrado con ese email, intente con otro"), HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setName(userDTO.getName());
        user.setLastname(userDTO.getLastname());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        Role roles = roleRepository.findByName("USER");
        user.setRol(Collections.singletonList(roles));
        user.setId_cart(iCartAPI.createCart().getBody());
        userRepository.save(user);

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId_user(user.getId_user());
        userResponseDTO.setId_cart(user.getId_cart());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setRol(user.getRol());
        userResponseDTO.setFavorite_product_ids(user.getFavorite_product_ids());
        userResponseDTO.setLastname(user.getLastname());

        // Generar Token JWT
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenGenerator.generateToken(authentication);

        // Crear cookie con token jwt
        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(24*60*60);
        cookie.setPath("/");


        response.addCookie(cookie);


        return new ResponseEntity<>(Map.of("message", "Registro de usuario exitoso", "info", userResponseDTO), HttpStatus.OK);
    }

    public ResponseEntity<?> fallbackCreateCart(Throwable throwable){
        return new ResponseEntity<>("Error en comunicacion entre microservicios", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> login(LoginDTO loginDTO, HttpServletResponse response) {
        // Validamos informacion y seteamos el usuario en el contexto de la app
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

        // Traemos usuario por email
        User user = this.getUserByEmail(loginDTO.getEmail());

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId_user(user.getId_user());
        userResponseDTO.setId_cart(user.getId_cart());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setRol(user.getRol());
        userResponseDTO.setLastname(user.getLastname());
        userResponseDTO.setFavorite_product_ids(user.getFavorite_product_ids());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenGenerator.generateToken(authentication);

        // Crear cookie con token jwt
        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(24*60*60);
        cookie.setPath("/");

        response.addCookie(cookie);
        return new ResponseEntity<>(Map.of("message", "Logeo de usuario exitoso", "info", userResponseDTO), HttpStatus.OK);
    }

    @Override
    public Boolean validateToken(String token) {
        return jwtTokenGenerator.validateToken(token);
    }

    @Override
    public String getUsernameByToken(String token) {

        String username = jwtTokenGenerator.getUsernameByJwt(token);
        return username;
    }

    @Override
    public String getRoleByEmail(String email) {
        User myUser = userRepository.findByEmail(email).orElse(null);
        String rolName = null;
        for(Role rol : myUser.getRol()){
            rolName = rol.getName();
        }
        return rolName;
    }

    @Override
    public List<UserResponseDTO> getUsers() {
        List<User> listUsers = userRepository.findAll();
        List<UserResponseDTO> listUserResponse = new ArrayList<>();

        for(User user : listUsers){
            listUserResponse.add(new UserResponseDTO(user.getId_user() ,user.getName(), user.getLastname(), user.getEmail(), user.getRol(), user.getFavorite_product_ids(), user.getId_cart()));
        }
        return listUserResponse;
    }

    @Override
    public UserResponseDTO getUserById(Long id_user) {
        User user = userRepository.findById(id_user).orElse(null);
        if(user != null){
            return new UserResponseDTO(user.getId_user(), user.getName(), user.getLastname(), user.getEmail(), user.getRol(), user.getFavorite_product_ids(), user.getId_cart());
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User editUserByEmail(String email, UserDTO newUserData) {
        User userToEdit = userRepository.findByEmail(email).orElse(null);
        if(userToEdit == null) return null;

        userToEdit.setName(newUserData.getName());
        userToEdit.setLastname(newUserData.getLastname());

        return userRepository.save(userToEdit);
    }

    @Override
    public void deleteUserById(Long id_user) {
        userRepository.deleteById(id_user);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public ResponseEntity<?> saveProductFavoriteByEmail(String email, Long id_product) {
        User user = userRepository.findByEmail(email).orElse(null);
        if(user == null){
            return new ResponseEntity<>(Map.of("message", "Usuario no encontrado"), HttpStatus.NOT_FOUND);
        }

        List<Long> listProductFavorites = user.getFavorite_product_ids();
        for(Long id_prod : listProductFavorites){
            if(id_prod.equals(id_product)){
                return new ResponseEntity<>(Map.of("message", "Producto ya agregado en favoritos"), HttpStatus.BAD_REQUEST);
            }
        }
        listProductFavorites.add(id_product);
        user.setFavorite_product_ids(listProductFavorites);

        userRepository.save(user);

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId_cart(user.getId_cart());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setRol(user.getRol());
        userResponseDTO.setLastname(user.getLastname());
        userResponseDTO.setFavorite_product_ids(user.getFavorite_product_ids());

        return new ResponseEntity<>(Map.of("message", "Producto agregado correctamente a favoritos", "info", userResponseDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> addPaymentByUserEmail(String email_user, Long payment_id) {
        User myUser = userRepository.findByEmail(email_user).orElse(null);
        if(myUser != null){
            List<Long> payments_ids = myUser.getPayments_ids();
            payments_ids.add(payment_id);
            myUser.setPayments_ids(payments_ids);
            userRepository.save(myUser);
        }
        return null;
    }

    @Override
    public ResponseEntity<?> deleteProductFavoriteByEmail(String email, Long id_product) {
        User user = userRepository.findByEmail(email).orElse(null);
        if(user == null){
            return new ResponseEntity<>(Map.of("message", "Usuario no encontrado"), HttpStatus.NOT_FOUND);
        }

        List<Long> listProductFavorites = user.getFavorite_product_ids();
        for(int i = 0 ; i < listProductFavorites.size(); i++){
            Long id_prod = listProductFavorites.get(i);
            if(id_prod.equals(id_product)){
                listProductFavorites.remove(i);
            }
        }

        user.setFavorite_product_ids(listProductFavorites);

        userRepository.save(user);

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId_cart(user.getId_cart());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setRol(user.getRol());
        userResponseDTO.setLastname(user.getLastname());
        userResponseDTO.setFavorite_product_ids(user.getFavorite_product_ids());

        return new ResponseEntity<>(Map.of("message", "Producto eliminado correctamente de favoritos", "info", userResponseDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Long>> getFavoriteProductsIds(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Long> listProductFavorites = user.getFavorite_product_ids();
        return new ResponseEntity<>(listProductFavorites, HttpStatus.OK);
    }

    public List<User> findUserIdsByFavoriteProductId(Long id_product){
        return userRepository.findUserIdsByFavoriteProductId(id_product);
    }
}
