package com.usersservice.controller;

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
    public ResponseEntity<String> createUser(@RequestBody User user){
        return userService.createUser(user);
    }
//    public List<User> getUsers();
//    public User getUserById(Long id_user);
//    public User editUser(User user);
//    public void deleteUserById(Long id_user);

}
