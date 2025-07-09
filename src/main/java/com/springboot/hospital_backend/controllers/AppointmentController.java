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
import com.springboot.hospital_backend.models.Appointment;
import com.springboot.hospital_backend.services.AppointmentService;

@RestController
@RequestMapping("/appointments")

public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping()
    public ResponseEntity<List<Appointment>> getAppointments() {
        return ResponseEntity.ok(this.appointmentService.getAppointments());
    }

    @GetMapping("/find/{patientId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByPatientId(@PathVariable int patientId) {
        return ResponseEntity.ok(this.appointmentService.getAppointmentsByPatientId(patientId));
    }

    @GetMapping("/find/doctor/{doctorId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctorId(@PathVariable int doctorId) {
        return ResponseEntity.ok(this.appointmentService.getAppointmentsByDoctorId(doctorId));
    }

    @GetMapping("/find/info/{appointmentId}")
    public ResponseEntity<Appointment> getAppointmentByAppointmentId(@PathVariable int appointmentId) {
        return ResponseEntity.ok(this.appointmentService.getAppointmentByAppointmentId(appointmentId));
    }
 
    @PostMapping("/save")
    public ResponseEntity<Appointment> saveAppointment(@RequestPart String patientId, @RequestPart String doctorId, 
        @RequestPart String scheduleId, @RequestPart String typeOfService, @RequestPart String serviceOrPackageId, 
        @RequestPart String isPaid) {
        return ResponseEntity.ok(this.appointmentService.saveAppointment(patientId, doctorId, scheduleId, 
            typeOfService, serviceOrPackageId, isPaid));
    }

    @PostMapping("/doctor/update")
    public ResponseEntity<Appointment> updateAppointmentByDoctor(@RequestPart String appointmentId, @RequestPart String patientId, 
        @RequestPart String scheduleId, @RequestPart String typeOfService, @RequestPart String serviceOrPackageId, 
        @RequestPart String isPaid) {
        return ResponseEntity.ok(this.appointmentService.updateAppointmentByDoctor(appointmentId, patientId, 
            scheduleId, typeOfService, serviceOrPackageId, isPaid));
    }

    @DeleteMapping("/deleteByAppointmentId/{appointmentId}")
    public ResponseEntity<Appointment> deleteAppointmentByAppointmentId(@PathVariable int appointmentId) {
        return ResponseEntity.ok(this.appointmentService.deleteAppointmentByAppointmentId(appointmentId));
    }

    @DeleteMapping("/doctor/delete/{appointmentId}")
    public ResponseEntity<Void> deleteAppointmentFromDoctor(@PathVariable int appointmentId) {
        this.appointmentService.deleteAppointmentFromDoctor(appointmentId);
        return ResponseEntity.ok().body(null);
    }
}
