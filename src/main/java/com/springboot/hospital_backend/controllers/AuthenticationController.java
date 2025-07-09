package com.springboot.hospital_backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.hospital_backend.dto.UserDTO;
import com.springboot.hospital_backend.models.Patient;
import com.springboot.hospital_backend.models.User;
import com.springboot.hospital_backend.services.AuthenticationService;

@RestController
@RequestMapping("/auth")

public class AuthenticationController {
    @Autowired
    private AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerPatient(@RequestBody Patient patient) {
        return ResponseEntity.ok(this.authService.registerPatient(patient));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<UserDTO> authenticateUser(@RequestBody User user) {
        return ResponseEntity.ok(this.authService.authenticateUser(user));
    }
}
