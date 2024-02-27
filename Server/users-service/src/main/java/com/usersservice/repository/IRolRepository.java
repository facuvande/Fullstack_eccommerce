package com.usersservice.repository;

import com.usersservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRolRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
