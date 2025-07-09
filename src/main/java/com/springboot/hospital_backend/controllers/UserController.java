package com.springboot.hospital_backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.hospital_backend.dto.GenericUserDTO;
import com.springboot.hospital_backend.dto.PatientDTO;
import com.springboot.hospital_backend.models.User;
import com.springboot.hospital_backend.services.PatientService;
import com.springboot.hospital_backend.services.UserService;

@RestController
@RequestMapping("/users")

public class UserController {
    @Autowired
    private PatientService patientService;

    @Autowired
    private UserService userService;

    @GetMapping("/patientDTO/{patientId}")
    public ResponseEntity<PatientDTO> getPatientDTO(@PathVariable int patientId) {
        return ResponseEntity.ok(this.patientService.getPatientDTO(patientId));
    }

    @PutMapping("/updateMainInfo")
    public ResponseEntity<User> updateMainInfo(@RequestBody GenericUserDTO user) {
        return ResponseEntity.ok(this.userService.updateMainInfo(user));
    }

    @PutMapping("/updateUsernameAndPassword")
    public ResponseEntity<User> updateUsernameAndPassword(@RequestBody User user) {
        return ResponseEntity.ok(this.userService.updateUsernameAndPassword(user));
    }
}
