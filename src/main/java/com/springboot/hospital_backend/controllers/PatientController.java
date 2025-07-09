package com.springboot.hospital_backend.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.hospital_backend.models.Patient;
import com.springboot.hospital_backend.services.PatientService;

@RestController
@RequestMapping("/patients")

public class PatientController {
    @Autowired
    private PatientService patientService;

    @GetMapping
    public ResponseEntity<List<Patient>> getPatients() {
        return ResponseEntity.ok(this.patientService.getPatients());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable int id) {
        return ResponseEntity.ok(this.patientService.getPatientById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<Patient> savePatient(@RequestPart Patient patient, 
        @RequestPart String action) {
        return ResponseEntity.ok(this.patientService.savePatient(patient, action));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable int id) {
        this.patientService.deletePatient(id);
        return ResponseEntity.ok().body(null);
    }
}
