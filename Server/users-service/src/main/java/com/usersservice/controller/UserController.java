package com.usersservice.controller;

import com.usersservice.dto.UserDTO;
import com.usersservice.dto.UserResponseDTO;
import com.usersservice.model.User;
import com.usersservice.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private IUserService userService;

    // AUTH
    @PostMapping("")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO){
        return userService.createUser(userDTO);
    }




//    public List<User> getUsers();
//    public User getUserById(Long id_user);
//    public User editUser(User user);
//    public void deleteUserById(Long id_user);

}
