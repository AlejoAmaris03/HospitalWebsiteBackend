package com.springboot.hospital_backend.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.springboot.hospital_backend.models.Doctor;
import com.springboot.hospital_backend.models.MedicalService;
import com.springboot.hospital_backend.models.Schedule;
import com.springboot.hospital_backend.models.User;
import com.springboot.hospital_backend.repositories.DoctorRepository;
import com.springboot.hospital_backend.repositories.ScheduleRepository;
import com.springboot.hospital_backend.repositories.UserRepository;

@Service

public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ScheduleRepository scheduleRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<Doctor> getDoctors() {
        return this.doctorRepo.findAllByOrderById();
    }

    public Doctor getDoctorById(int id) {
        return this.doctorRepo.findById(id).get();
    }

    public List<Doctor> getDoctorsBySpecialityId(int specialityId) {
        return doctorRepo.findDoctorsBySpecialityId(specialityId);
    }

    public List<Doctor> getDoctorsByPackages(Set<MedicalService> services) {
        List<Integer> specialitiesIds = services.stream()
                .map(s -> s.getSpeciality().getId()).collect(Collectors.toList());

        return doctorRepo.findDoctorsBySpecialities(specialitiesIds);
    }

    public Doctor saveDoctor(Doctor doctor, String action) {
        Set<Schedule> attachedSchedules = doctor.getSchedules().stream().map(el -> 
            this.scheduleRepo.findById(el.getId()).get()).collect(Collectors.toSet());

        switch (action) {
            case "add": {
                if(this.userRepo.findUserByUsername(doctor.getUsername()) == null) {
                    doctor.setPassword(this.passwordEncoder.encode(doctor.getPassword()));
                    doctor.setSchedules(attachedSchedules);
                    return this.doctorRepo.save(doctor);
                }

                break;
            }
        
            case "update": {
                User curerntDoctor = this.userRepo.findById(doctor.getId()).get();

                if(this.userRepo.findUserByUsernameExcludingCurrentOne(curerntDoctor.getUsername(), doctor.getUsername()) == null) {
                    doctor.setPassword(this.passwordEncoder.encode(doctor.getPassword()));
                    doctor.setSchedules(attachedSchedules);
                    return this.doctorRepo.save(doctor);
                }

                break;
            }
        }

        return null;
    }

    public void deleteDoctor(int id) {
        this.doctorRepo.deleteById(id);
    }
}
