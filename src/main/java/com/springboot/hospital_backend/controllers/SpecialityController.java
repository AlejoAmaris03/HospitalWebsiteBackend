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
import com.springboot.hospital_backend.models.Speciality;
import com.springboot.hospital_backend.services.SpecialityService;

@RestController
@RequestMapping("/specialities")

public class SpecialityController {
    @Autowired
    private SpecialityService specialityService;

    @GetMapping
    private ResponseEntity<List<Speciality>> getSpecialities() {
        return ResponseEntity.ok(this.specialityService.getSpecialities());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Speciality> getSpecialityById(@PathVariable int id) {
        return ResponseEntity.ok(this.specialityService.getSpecialityById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<Speciality> saveSpeciality(@RequestPart Speciality speciality, 
        @RequestPart String action) {
        return ResponseEntity.ok(this.specialityService.saveSpeciality(speciality, action));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSpeciality(@PathVariable int id) {
        this.specialityService.deleteSpeciality(id);
        return ResponseEntity.ok().body(null);
    }
}
