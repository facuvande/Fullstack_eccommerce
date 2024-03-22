package com.usersservice.repository;

import com.usersservice.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u JOIN u.favorite_product_ids p WHERE p = :id_product")
    List<User> findUserIdsByFavoriteProductId(Long id_product);
    // Metodo para poder buscar un usuario mediante su email
    Optional<User> findByEmail(String email);
    // Metodo para poder verificar si un email existe en nuestra db
    Boolean existsByEmail(String email);
}
