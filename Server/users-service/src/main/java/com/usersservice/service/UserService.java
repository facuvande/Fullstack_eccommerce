package com.usersservice.service;

import com.usersservice.model.User;
import com.usersservice.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService{

    @Autowired
    private IUserRepository userRepository;

    @Override
    public User createUser(User user) {

        // Todo: Crear carrito y asignar id de carrito al usuario

        return userRepository.save(user);
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
    public User editUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id_user) {
        userRepository.deleteById(id_user);
    }
}
