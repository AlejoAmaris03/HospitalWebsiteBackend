package com.springboot.hospital_backend.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.springboot.hospital_backend.models.Appointment;
import com.springboot.hospital_backend.models.Doctor;
import com.springboot.hospital_backend.models.MedicalService;
import com.springboot.hospital_backend.models.Package;
import com.springboot.hospital_backend.models.Patient;
import com.springboot.hospital_backend.models.Schedule;
import com.springboot.hospital_backend.repositories.AppointmentRepository;
import com.springboot.hospital_backend.repositories.DoctorRepository;
import com.springboot.hospital_backend.repositories.MedicalServiceRepository;
import com.springboot.hospital_backend.repositories.PackageRepository;
import com.springboot.hospital_backend.repositories.PatientRepository;
import com.springboot.hospital_backend.repositories.ScheduleRepository;

@Service

public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepo;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private ScheduleRepository scheduleRepo;

    @Autowired
    private MedicalServiceRepository medicalServiceRepo;

    @Autowired
    private PackageRepository packageRepo;

    public List<Appointment> getAppointments() {
        return this.appointmentRepo.findAllByOrderByDateAscStartTimeAsc();
    }

    public List<Appointment> getAppointmentsByPatientId(int patientId) {
        return this.appointmentRepo.findAppointmentsByPatientIdOrderByDateAscStartTimeAsc(patientId);
    }

    public List<Appointment> getAppointmentsByDoctorId(int doctorId) {
        return this.appointmentRepo.findAppointmentsByDoctorIdOrderByDateAscStartTimeAsc(doctorId);
    }

    public Appointment getAppointmentByAppointmentId(int appointmentId) {
        return this.appointmentRepo.findAppointmentById(appointmentId);
    }

    public Appointment saveAppointment(String patientId, String doctorId, String scheduleId, String typeOfService,
        String serviceOrPackageId, String isPaid) {
        Patient patient = this.patientRepo.findById(Integer.parseInt(patientId)).get();
        Doctor doctor = this.doctorRepo.findById(Integer.parseInt(doctorId)).get();
        Schedule schedule = this.scheduleRepo.findById(Integer.parseInt(scheduleId)).get();
        Boolean paid = isPaid.equals("true") ? true : false;

        if(this.appointmentRepo.checkValidSchedule(patient.getId(), schedule.getDay(), schedule.getEndTime(), schedule.getStartTime()) == null) {
            if(typeOfService.equals("service")) {
                MedicalService service = this.medicalServiceRepo.findById(Integer.parseInt(serviceOrPackageId)).get();

                if(this.appointmentRepo.checkService(patient.getId(), service.getId()) == null) {
                    Long total = patient.isHasInsurance() 
                        ? (long) (service.getPrice() - service.getPrice() * .2)
                        : service.getPrice();

                    Appointment appointment = new Appointment(0, schedule.getDay(), schedule.getStartTime(), 
                        schedule.getEndTime(), patient, doctor, service, null, total, paid);

                    return appointmentRepo.save(appointment);
                }
            }
            else {
                Package packages = packageRepo.findById(Integer.parseInt(serviceOrPackageId)).get();

                if(this.appointmentRepo.checkPackage(patient.getId(), packages.getId()) == null) {
                    Long total = patient.isHasInsurance() 
                        ? (long) (packages.getPrice() - packages.getPrice() * .2)
                        : packages.getPrice();

                    Appointment appointment = new Appointment(0, schedule.getDay(), schedule.getStartTime(), 
                        schedule.getEndTime(), patient, doctor, null, packages, total, paid);

                    return appointmentRepo.save(appointment);
                }
            }
        }

        return null;
    }

    public Appointment updateAppointmentByDoctor(String appointmentId, String patientId, String scheduleId,
        String typeOfService, String serviceOrPackageId, String isPaid) {

        Appointment appointment = this.appointmentRepo.findById(Integer.parseInt(appointmentId)).get();
        Patient patient = this.patientRepo.findById(Integer.parseInt(patientId)).get();
        Schedule schedule = this.scheduleRepo.findById(Integer.parseInt(scheduleId)).get();
        Boolean paid = isPaid.equals("true") ? true : false;

        if(this.appointmentRepo.checkValidSchedule(appointment.getId(), patient.getId(), 
            schedule.getDay(), schedule.getEndTime(), schedule.getStartTime()) == null) {

            if(typeOfService.equals("service")) {
                MedicalService service = this.medicalServiceRepo.findById(Integer.parseInt(serviceOrPackageId)).get();

                if(this.appointmentRepo.checkService(appointment.getId(), patient.getId(), service.getId()) == null) {
                    Long total = patient.isHasInsurance() 
                        ? (long) (service.getPrice() - service.getPrice() * .2)
                        : service.getPrice();
    
                        appointment.setDate(schedule.getDay());
                        appointment.setEndTime(schedule.getEndTime());
                        appointment.setId(appointment.getId());
                        appointment.setMedicalService(service);
                        appointment.setMedicalPackage(null);
                        appointment.setPatient(patient);
                        appointment.setServicePaid(paid);
                        appointment.setStartTime(schedule.getStartTime());
                        appointment.setTotal(total);
    
                    return appointmentRepo.save(appointment);
                }
            }
            else {
                Package packages = this.packageRepo.findById(Integer.parseInt(serviceOrPackageId)).get();

                if(this.appointmentRepo.checkPackage(appointment.getId(), patient.getId(), packages.getId()) == null) {
                    Long total = patient.isHasInsurance() 
                        ? (long) (packages.getPrice() - packages.getPrice() * .2)
                        : packages.getPrice();
    
                        appointment.setDate(schedule.getDay());
                        appointment.setEndTime(schedule.getEndTime());
                        appointment.setId(appointment.getId());
                        appointment.setMedicalPackage(packages);
                        appointment.setMedicalService(null);
                        appointment.setPatient(patient);
                        appointment.setServicePaid(paid);
                        appointment.setStartTime(schedule.getStartTime());
                        appointment.setTotal(total);
    
                    return appointmentRepo.save(appointment);
                }
            }
        }

        return null;
    }

    @Transactional
    public Appointment deleteAppointmentByAppointmentId(int appointmentId) {
        Appointment appointment = this.appointmentRepo.findById(appointmentId).get();

        if(this.appointmentRepo.checkAppointmentTimeToCancel(appointmentId) == null) {
            this.appointmentRepo.deleteAppointmentById(appointmentId);
            return appointment;
        }

        return null;
    }

    public void deleteAppointmentFromDoctor(int appointmentId) {
        this.appointmentRepo.deleteById(appointmentId);
    }
}
