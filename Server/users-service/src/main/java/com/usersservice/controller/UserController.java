package com.usersservice.controller;

import com.usersservice.dto.*;
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
    public ResponseEntity<ValidationTokenDTO> validateToken(@RequestHeader("Authorization") String authorizationHeader){
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            boolean isValidToken = userService.validateToken(token);
            if(isValidToken){
                User user = userService.getUserByEmail(userService.getUsernameByToken(token));
                return new ResponseEntity<>(new ValidationTokenDTO(true, user.getName(), user.getLastname(), user.getEmail()), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ValidationTokenDTO(false, null, null, null), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<UserResponseDTO>> getUsers(){
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }
    @GetMapping("/{id_user}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id_user){
        UserResponseDTO myUser = userService.getUserById(id_user);
        if(myUser != null) {
            return new ResponseEntity<>(myUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{id_user}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id_user){
        userService.deleteUserById(id_user);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }

}
