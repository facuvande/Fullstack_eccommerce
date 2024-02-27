package com.usersservice.service;

import com.usersservice.dto.UserDTO;
import com.usersservice.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserService {

    // Metodo para crear usuario
    public ResponseEntity<?> createUser(UserDTO userDTO);

    // Metodo para traer todos los usuarios
    public List<User> getUsers();

    // Metodo para traer un usuario por id
    public User getUserById(Long id_user);

    // Metodo para editar usuario
    public User editUser(User user);

    // Metodo para eliminar un usuario
    public void deleteUserById(Long id_user);

    // Metodo para ver si existe un usuario por email
    Boolean existsByEmail(String email);

}
