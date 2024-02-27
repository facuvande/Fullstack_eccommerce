package com.usersservice.controller;

import com.usersservice.dto.AuthResponseDTO;
import com.usersservice.dto.LoginDTO;
import com.usersservice.dto.UserDTO;
import com.usersservice.dto.UserResponseDTO;
import com.usersservice.model.User;
import com.usersservice.repository.IRolRepository;
import com.usersservice.security.JwtTokenGenerator;
import com.usersservice.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserService userService;

    // AUTH
    @PostMapping("/auth/register")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO){
        return userService.createUser(userDTO);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO){
        return userService.login(loginDTO);
    }

    @PostMapping("/auth/validateToken")
    public Boolean validateToken(@RequestHeader("Authorization") String authorizationHeader){
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            boolean isValidToken = userService.validateToken(token);
            return true;
        }
        return false;
    }


//    public List<User> getUsers();
//    public User getUserById(Long id_user);
//    public User editUser(User user);
//    public void deleteUserById(Long id_user);

}
