
package com.springboot.hospital_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.springboot.hospital_backend.dto.GenericUserDTO;
import com.springboot.hospital_backend.models.Patient;
import com.springboot.hospital_backend.models.User;
import com.springboot.hospital_backend.repositories.PatientRepository;
import com.springboot.hospital_backend.repositories.UserRepository;

@Service

public class UserService {
    @Autowired 
    private UserRepository userRepo;

    @Autowired 
    private PatientRepository patientRepo;

    @Autowired BCryptPasswordEncoder passwordEncoder;

    public User saveUser(User user) {
        if(userRepo.findUserByUsername(user.getUsername()) == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepo.save(user);
        }

        return null;
    }

    public User updateMainInfo(GenericUserDTO user) {
        if(user.getRole().getName().equals("ROLE_PATIENT")) {
            Patient patient = this.patientRepo.findById(user.getId()).get();
            patient.setName(user.getName());
            patient.setSurname(user.getSurname());
            patient.setEmail(user.getEmail());
            patient.setAddress(user.getAddress());
            patient.setHasInsurance(user.isHasInsurance());

            return this.patientRepo.save(patient);
        }

        User userToUpdate = this.userRepo.findById(user.getId()).get();
        userToUpdate.setName(user.getName());
        userToUpdate.setSurname(user.getSurname());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setAddress(user.getAddress());

        return this.userRepo.save(userToUpdate);
    }

    public User updateUsernameAndPassword(User user) {
        User currentUser = this.userRepo.findById(user.getId()).get();

        if(this.userRepo.findUserByUsernameExcludingCurrentOne(currentUser.getUsername(), user.getUsername()) == null) {
            currentUser.setUsername(user.getUsername());
            currentUser.setPassword(passwordEncoder.encode(user.getPassword()));

            return this.userRepo.save(currentUser); 
        }

        return null;
    }
}
