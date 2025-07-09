package com.springboot.hospital_backend.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springboot.hospital_backend.models.Speciality;
import com.springboot.hospital_backend.repositories.SpecialityRepository;

@Service

public class SpecialityService {
    @Autowired
    private SpecialityRepository specialityRepo;

    public List<Speciality> getSpecialities() {
        return this.specialityRepo.findAllByOrderById();
    }

    public Speciality getSpecialityById(int id) {
        return this.specialityRepo.findById(id).get();
    }

    public Speciality saveSpeciality(Speciality speciality, String action) {
        switch (action) {
            case "add": {
                if(this.specialityRepo.findSpecialityByName(speciality.getName()) == null)
                    return this.specialityRepo.save(speciality);

                break;
            }
        
            case "update": {
                Speciality curerntSpeciality = this.specialityRepo.findById(speciality.getId()).get();

                if(this.specialityRepo.findSpecialityByNameExcludingCurrentOne(curerntSpeciality.getName(), speciality.getName()) == null)
                    return this.specialityRepo.save(speciality);

                break;
            }
        }

        return null;
    }

    public void deleteSpeciality(int id) {
        this.specialityRepo.deleteById(id);
    }
}
