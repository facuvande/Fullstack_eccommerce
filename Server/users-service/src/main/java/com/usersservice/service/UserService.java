package com.usersservice.service;

import com.usersservice.dto.AuthResponseDTO;
import com.usersservice.dto.LoginDTO;
import com.usersservice.dto.UserDTO;
import com.usersservice.dto.UserResponseDTO;
import com.usersservice.model.Role;
import com.usersservice.model.User;
import com.usersservice.repository.IRolRepository;
import com.usersservice.repository.IUserRepository;
import com.usersservice.security.JwtTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements IUserService{
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private IRolRepository roleRepository;
    private JwtTokenGenerator jwtTokenGenerator;
    @Autowired
    private IUserRepository userRepository;

    public UserService(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, IRolRepository roleRepository, JwtTokenGenerator jwtTokenGenerator, IUserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<?> createUser(UserDTO userDTO) {

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
        // Todo: Crear carrito y asignar id de carrito al usuario
        user.setId_cart(3L);
        userRepository.save(user);

        return new ResponseEntity<>(Map.of("message", "Registro de usuario exitoso"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AuthResponseDTO> login(LoginDTO loginDTO) {
        // Validamos informacion y seteamos el usuario en el contexto de la app
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

        // Traemos usuario por email
        User user = this.getUserByEmail(loginDTO.getEmail());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDTO(token, user.getName(), user.getLastname(), user.getEmail()), HttpStatus.OK);
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
            listUserResponse.add(new UserResponseDTO(user.getName(), user.getLastname(), user.getEmail(), user.getRol(), user.getId_cart()));
        }
        return listUserResponse;
    }

    @Override
    public UserResponseDTO getUserById(Long id_user) {
        User user = userRepository.findById(id_user).orElse(null);
        if(user != null){
            return new UserResponseDTO(user.getName(), user.getLastname(), user.getEmail(), user.getRol(), user.getId_cart());
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public void deleteUserById(Long id_user) {
        userRepository.deleteById(id_user);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
