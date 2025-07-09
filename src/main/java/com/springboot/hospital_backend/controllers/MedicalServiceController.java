package com.springboot.hospital_backend.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.hospital_backend.models.MedicalService;
import com.springboot.hospital_backend.services.MedicalServicesService;

@RestController
@RequestMapping("/medicalServices")

public class MedicalServiceController {
    @Autowired
    private MedicalServicesService medicalServicesService;

    @GetMapping
    public ResponseEntity<List<MedicalService>> getMedicalServices() {
        return ResponseEntity.ok(this.medicalServicesService.getMedicalServices());
    }

    @GetMapping("/find/bySpeciality/{specialityId}")
    public ResponseEntity<List<MedicalService>> getMedicalServicesBySpecialityId(@PathVariable int specialityId) {
        return ResponseEntity.ok(this.medicalServicesService.getMedicalServicesByDoctorId(specialityId));
    }

    @PostMapping("/save")
    public ResponseEntity<MedicalService> saveMedicalService(@RequestBody MedicalService medicalService) {
        return ResponseEntity.ok(this.medicalServicesService.saveMedicalService(medicalService));
    }

    @PutMapping("/update")
    public ResponseEntity<MedicalService> updateMedicalService(@RequestBody MedicalService medicalService) {
        return ResponseEntity.ok(this.medicalServicesService.updateMedicalService(medicalService));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MedicalService> deleteMedicalService(@PathVariable int id) {
        return ResponseEntity.ok(this.medicalServicesService.deleteMedicalService(id));
    }
}
