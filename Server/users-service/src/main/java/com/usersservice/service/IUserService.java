package com.usersservice.service;

import com.usersservice.dto.AuthResponseDTO;
import com.usersservice.dto.LoginDTO;
import com.usersservice.dto.UserDTO;
import com.usersservice.dto.UserResponseDTO;
import com.usersservice.model.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IUserService {

    // Metodo para crear usuario (Register)
    public ResponseEntity<?> createUser(UserDTO userDTO, HttpServletResponse response);

    //Metodo para Logear un usuario retornando un token
    public ResponseEntity<AuthResponseDTO> login(LoginDTO loginDTO);

    // Metodo para validar token jwt
    public Boolean validateToken(String token);

    // Metodo para traer username (email) por token
    public String getUsernameByToken(String token);

    // Metodo para traer el rol a traves de un email
    public String getRoleByEmail(String email);

    // Metodo para traer todos los usuarios
    public List<UserResponseDTO> getUsers();

    // Metodo para traer un usuario por id
    public UserResponseDTO getUserById(Long id_user);

    // Metodo para traer un usuario por email
    public User getUserByEmail(String email);

    // Metodo para eliminar un usuario
    public void deleteUserById(Long id_user);

    // Metodo para ver si existe un usuario por email
    Boolean existsByEmail(String email);


}
