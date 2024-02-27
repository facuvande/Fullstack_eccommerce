package com.usersservice.model;

import com.usersservice.repository.IRolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializer implements CommandLineRunner {
    private final IRolRepository rolRepository;

    public RoleInitializer(IRolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Role adminRole = rolRepository.findByName("ADMIN");
        if(adminRole == null){
            adminRole = new Role();
            adminRole.setName("ADMIN");
            rolRepository.save(adminRole);
        }

        Role userRole = rolRepository.findByName("USER");
        if(userRole == null){
            userRole = new Role();
            userRole.setName("USER");
            rolRepository.save(userRole);
        }
    }
}
