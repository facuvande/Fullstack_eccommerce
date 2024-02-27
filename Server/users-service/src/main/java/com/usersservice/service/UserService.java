package com.usersservice.service;

import com.usersservice.dto.UserDTO;
import com.usersservice.model.User;
import com.usersservice.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService implements IUserService{

    @Autowired
    private IUserRepository userRepository;

    @Override
    public ResponseEntity<?> createUser(UserDTO userDTO) {

        // Verificar si existe el usuario con ese email
        if(this.existsByEmail(userDTO.getEmail())){
            return new ResponseEntity<>(Map.of("message","Ya existe un usuario con ese email, intente con otro"), HttpStatus.BAD_REQUEST);
        }

        // Hashear password


        // Todo: Crear carrito y asignar id de carrito al usuario

        User newUser = new User();
        newUser.setEmail(userDTO.getEmail());
        newUser.setName(userDTO.getName());
        newUser.setRol(null);
        newUser.setPassword(userDTO.getPassword());
        newUser.setId_cart(1L);
        newUser.setLastname(userDTO.getLastname());

        User userCreated = userRepository.save(newUser);
        return new ResponseEntity<>(Map.of("message", "Registro de usuario exitoso"), HttpStatus.OK);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id_user) {
        return userRepository.findById(id_user).orElse(null);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User editUser(User user) {
        return userRepository.save(user);
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
