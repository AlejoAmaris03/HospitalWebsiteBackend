package com.springboot.hospital_backend.models;

import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "doctors")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor

public class Doctor extends User {
    @Column(nullable = false)
    private Long salary;

    @ManyToOne
    @JoinColumn(
        name = "speciality_id",
        nullable = false
    )
    private Speciality speciality;

    @ManyToMany(
        fetch = FetchType.EAGER,
        cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    @JoinTable(
        name = "doctors_schecules",
        joinColumns = @JoinColumn(name = "doctor_id"),
        inverseJoinColumns = @JoinColumn(name = "schedule_id")
    )
    private Set<Schedule> schedules;
}
