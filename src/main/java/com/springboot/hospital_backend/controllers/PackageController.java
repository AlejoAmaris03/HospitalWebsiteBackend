package com.springboot.hospital_backend.controllers;

import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.hospital_backend.models.MedicalService;
import com.springboot.hospital_backend.models.Package;
import com.springboot.hospital_backend.services.PackageService;

@RestController
@RequestMapping("/packages")

public class PackageController {
    @Autowired
    private PackageService packageService;

    @GetMapping
    public ResponseEntity<List<Package>> getPackages() {
        return ResponseEntity.ok(this.packageService.getPackages());
    }

    @GetMapping("/find/bySpeciality/{specialityId}")
    public ResponseEntity<List<Package>> getPackagesBySpecialityId(@PathVariable int specialityId) {
        return ResponseEntity.ok(this.packageService.getPackagesBySpecialityId(specialityId));
    }

    @PostMapping("/save")
    public ResponseEntity<Package> savePackage(@RequestBody Set<MedicalService> medicalServices) {
        return ResponseEntity.ok(this.packageService.savePackage(medicalServices));
    }

    @PutMapping("/addServicesToPackage")
    public ResponseEntity<Package> addServicesToPackage(@RequestPart Package packages, 
        @RequestPart Set<MedicalService> services) {
        return ResponseEntity.ok(this.packageService.addServicesToPackage(packages, services));
    }

    @PutMapping("/deleteServiceFromPackage")
    public ResponseEntity<Package> deleteServiceFromPackage(@RequestPart Package packages, 
        @RequestPart MedicalService service) {
        return ResponseEntity.ok(this.packageService.deleteServiceFromPackage(packages, service));
    }

    @PutMapping("/recalculatePrice")
    public ResponseEntity<Void> recalculatePrice(@RequestBody MedicalService service) {
        this.packageService.recalculatePrice(service);
        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Package> deletePackage(@PathVariable int id) {
        return ResponseEntity.ok(this.packageService.deletePackage(id));
    }
}
