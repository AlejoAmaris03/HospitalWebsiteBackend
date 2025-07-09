package com.springboot.hospital_backend.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.hospital_backend.dto.PatientDTO;
import com.springboot.hospital_backend.models.Patient;
import com.springboot.hospital_backend.models.User;
import com.springboot.hospital_backend.repositories.PatientRepository;
import com.springboot.hospital_backend.repositories.UserRepository;

@Service

public class PatientService {
    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<Patient> getPatients() {
        return this.patientRepo.findAllByOrderById();
    }

    public Patient getPatientById(int id) {
        return this.patientRepo.findById(id).get();
    }

    public PatientDTO getPatientDTO(int patientId) {
        return this.patientRepo.findPatientDTO(patientId);
    }

    public Patient savePatient(Patient patient, String action) {
        switch (action) {
            case "add": {
                if(this.userRepo.findUserByUsername(patient.getUsername()) == null) {
                    patient.setPassword(this.passwordEncoder.encode(patient.getPassword()));
                    return this.patientRepo.save(patient);
                }

                break;
            }
        
            case "update": {
                User curerntPatient = this.userRepo.findById(patient.getId()).get();

                if(this.userRepo.findUserByUsernameExcludingCurrentOne(curerntPatient.getUsername(), patient.getUsername()) == null) {
                    patient.setPassword(this.passwordEncoder.encode(patient.getPassword()));
                    return this.patientRepo.save(patient);
                }

                break;
            }
        }

        return null;
    }

    public void deletePatient(int id) {
        this.patientRepo.deleteById(id);
    }
}
