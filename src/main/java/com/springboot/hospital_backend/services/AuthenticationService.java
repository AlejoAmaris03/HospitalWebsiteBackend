package com.springboot.hospital_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.springboot.hospital_backend.dto.UserDTO;
import com.springboot.hospital_backend.models.Patient;
import com.springboot.hospital_backend.models.User;
import com.springboot.hospital_backend.repositories.PatientRepository;
import com.springboot.hospital_backend.repositories.UserRepository;

@Service

public class AuthenticationService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserDTO registerPatient(Patient patient) {
        if(this.userRepo.findUserByUsername(patient.getUsername()) == null) {
            patient.setPassword(this.passwordEncoder.encode(patient.getPassword()));
            Patient user = this.patientRepo.save(patient);

            return getUserDTO(user.getUsername());
        }

        return null;
    }

    public UserDTO authenticateUser(User user) {
        try {
            Authentication authentication = 
                authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

            if(authentication.isAuthenticated()) {
                UserDTO userDTO = getUserDTO(user.getUsername());
                userDTO.setToken(this.jwtService.generateToken(user.getUsername()));

                return userDTO;
            }

            return null;
        } 
        catch (Exception e) {
            return null;
        }
    }

    private UserDTO getUserDTO(String username) {
        User user = this.userRepo.findUserByUsername(username);

        return new UserDTO(
            user.getId(),
            user.getName(),
            user.getSurname(),
            user.getEmail(),
            user.getAddress(),
            user.getUsername(),
            null,
            user.getRole()
        );
    }
}
