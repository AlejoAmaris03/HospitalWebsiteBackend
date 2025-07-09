package com.springboot.hospital_backend.controllers;

import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.hospital_backend.models.Doctor;
import com.springboot.hospital_backend.models.MedicalService;
import com.springboot.hospital_backend.services.DoctorService;

@RestController
@RequestMapping("/doctors")

public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @GetMapping
    public ResponseEntity<List<Doctor>> getDoctors() {
        return ResponseEntity.ok(this.doctorService.getDoctors());
    }

    @GetMapping("/speciality/{specialityId}")
    public ResponseEntity<List<Doctor>> getDoctorsBySpecialityId(@PathVariable int specialityId) {
        return ResponseEntity.ok(this.doctorService.getDoctorsBySpecialityId(specialityId));
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable int id) {
        return ResponseEntity.ok(this.doctorService.getDoctorById(id));
    }

    @PostMapping("/specialities")
    public ResponseEntity<List<Doctor>> getDoctorsByPackages(@RequestBody Set<MedicalService> services) {
        return ResponseEntity.ok(this.doctorService.getDoctorsByPackages(services));
    }

    @PostMapping("/save")
    public ResponseEntity<Doctor> saveDoctor(@RequestPart Doctor doctor, @RequestPart String action) {
        return ResponseEntity.ok(this.doctorService.saveDoctor(doctor, action));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable int id) {
        this.doctorService.deleteDoctor(id);
        return ResponseEntity.ok().body(null);
    }
}
