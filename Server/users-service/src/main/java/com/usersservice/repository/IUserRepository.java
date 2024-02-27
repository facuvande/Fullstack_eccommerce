package com.usersservice.repository;

import com.usersservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    // Metodo para poder buscar un usuario mediante su email
    Optional<User> findByEmail(String email);
    // Metodo para poder verificar si un email existe en nuestra db
    Boolean existsByEmail(String email);
}
