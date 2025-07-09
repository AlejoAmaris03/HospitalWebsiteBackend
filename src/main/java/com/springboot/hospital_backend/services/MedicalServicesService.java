package com.springboot.hospital_backend.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springboot.hospital_backend.models.MedicalService;
import com.springboot.hospital_backend.repositories.AppointmentRepository;
import com.springboot.hospital_backend.repositories.MedicalServiceRepository;

@Service

public class MedicalServicesService {
    @Autowired
    private MedicalServiceRepository medicalServiceRepo;

    @Autowired
    private PackageService packageService;

    @Autowired
    private AppointmentRepository appointmentRepo;

    public List<MedicalService> getMedicalServices() {
        return this.medicalServiceRepo.findAllByOrderById();
    }

    public List<MedicalService> getMedicalServicesByDoctorId(int specialityId) {
        return this.medicalServiceRepo.findAllBySpecialityId(specialityId);
    }

    public MedicalService saveMedicalService(MedicalService medicalService) {
        if(this.medicalServiceRepo.findMedicalServiceByName(medicalService.getName()) == null)
            return this.medicalServiceRepo.save(medicalService);

        return null;
    }

    public MedicalService updateMedicalService(MedicalService medicalService) {
        MedicalService currentMedicalService = this.medicalServiceRepo.findById(medicalService.getId()).get();

        if(this.medicalServiceRepo.findMedicalServiceByNameExcludingCurrentOne(currentMedicalService.getName(), medicalService.getName()) == null)
            return this.medicalServiceRepo.save(medicalService);

        return null;
    }

    public MedicalService deleteMedicalService(int id) {
        MedicalService service = medicalServiceRepo.findById(id).get();

        if(this.appointmentRepo.findAppointmentsByMedicalServiceId(id).size() == 0 && 
            this.appointmentRepo.findAppointmentsByPackagesContaintService(id).size() == 0) {

            this.packageService.updateServicesFromPackege(id);
            this.medicalServiceRepo.deleteById(id);

            return service;
        }

        return null;
    }
}
