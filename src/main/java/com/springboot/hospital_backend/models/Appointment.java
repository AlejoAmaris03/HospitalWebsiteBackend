package com.springboot.hospital_backend.models;

import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "appointments")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;
    
    @ManyToOne
    @JoinColumn(
        name = "patient_id",
        nullable = false
    )
    private Patient patient;

    @ManyToOne
    @JoinColumn(
        name = "doctor_id",
        nullable = false
    )
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(
        name = "service_id",
        nullable = true
    )
    private MedicalService medicalService;

    @ManyToOne
    @JoinColumn(
        name = "package_id",
        nullable = true
    )
    private Package medicalPackage;

    @Column(nullable = false)
    private Long total;

    @Column(nullable = false)
    private boolean isServicePaid;
}
