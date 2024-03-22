package com.usersservice.service;

import com.usersservice.dto.LoginDTO;
import com.usersservice.dto.UserDTO;
import com.usersservice.dto.UserResponseDTO;
import com.usersservice.model.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface IUserService {

    public ResponseEntity<?> createUser(UserDTO userDTO, HttpServletResponse response);
    public ResponseEntity<?> login(LoginDTO loginDTO, HttpServletResponse response);
    public Boolean validateToken(String token);
    public String getUsernameByToken(String token);
    public String getRoleByEmail(String email);
    public List<UserResponseDTO> getUsers();
    public UserResponseDTO getUserById(Long id_user);
    public User getUserByEmail(String email);
    public User editUserByEmail(String email, UserDTO newUserData);
    public void deleteUserById(Long id_user);
    public Boolean existsByEmail(String email);
    public ResponseEntity<?> saveProductFavoriteByEmail(String email, Long id_product);
    public ResponseEntity<?> deleteProductFavoriteByEmail(String email, Long id_product);
    public ResponseEntity<List<Long>> getFavoriteProductsIds(String email);
    public List<User> findUserIdsByFavoriteProductId(Long id_product);

}
