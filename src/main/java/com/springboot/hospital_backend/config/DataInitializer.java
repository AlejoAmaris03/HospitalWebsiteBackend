package com.springboot.hospital_backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.springboot.hospital_backend.models.Role;
import com.springboot.hospital_backend.models.User;
import com.springboot.hospital_backend.repositories.RoleRepository;
import com.springboot.hospital_backend.repositories.UserRepository;
import com.springboot.hospital_backend.services.UserService;

@Configuration

public class DataInitializer {
    @SuppressWarnings("unused")
    @Bean
    public CommandLineRunner initRoles(RoleRepository roleRepo) {
        // Adding default roles
        return roles -> {
            if(roleRepo.findByName("ROLE_ADMIN") == null)
                roleRepo.save(new Role(0, "ROLE_ADMIN"));
            
            if(roleRepo.findByName("ROLE_DOCTOR") == null)
                roleRepo.save(new Role(0, "ROLE_DOCTOR"));

            if(roleRepo.findByName("ROLE_PATIENT") == null)
                roleRepo.save(new Role(0, "ROLE_PATIENT"));
        };
    }

    @SuppressWarnings("unused")
    @Bean
    public CommandLineRunner initUsers(UserRepository userRepo, UserService userService) {
        // Adding default admin
        return users -> {
            if(userRepo.findUserByUsername("admin") == null) {
                userService.saveUser(new User(
                    0, 
                    "admin", 
                    "admin", 
                    "admin@email.com",
                    "1 st e.g.",
                    "admin",
                    "123", /* Password is hashed with BCript */
                    new Role(1, "ROLE_ADMIN")));
            }
        };
    }
}
