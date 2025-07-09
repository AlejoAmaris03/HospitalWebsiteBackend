package com.springboot.hospital_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.springboot.hospital_backend.models.User;
import com.springboot.hospital_backend.models.UserPrincipal;
import com.springboot.hospital_backend.repositories.UserRepository;

@Service

public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepo.findUserByUsername(username);

        if(user == null)
            throw new UsernameNotFoundException("User with username " + username + " not found!");

        return new UserPrincipal(user);
    }
}
