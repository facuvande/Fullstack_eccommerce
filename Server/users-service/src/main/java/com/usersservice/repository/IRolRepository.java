package com.usersservice.repository;

import com.usersservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRolRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
