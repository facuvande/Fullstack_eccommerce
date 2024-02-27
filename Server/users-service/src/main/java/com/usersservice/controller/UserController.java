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

    @PostMapping("")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO){
        return userService.createUser(userDTO);
    }
//    public List<User> getUsers();
//    public User getUserById(Long id_user);
//    public User editUser(User user);
//    public void deleteUserById(Long id_user);

    @GetMapping("/{email}")
    public UserResponseDTO getUserByEmail(@PathVariable ("email") String email){
        User myUser = userService.getUserByEmail(email);

        UserResponseDTO userResponse = new UserResponseDTO();
        userResponse.setEmail(email);
        userResponse.setName(myUser.getName());
        userResponse.setRol(myUser.getRol());
        userResponse.setLastname(myUser.getLastname());
        userResponse.setId_cart(myUser.getId_cart());

        return userResponse;
    }
}
