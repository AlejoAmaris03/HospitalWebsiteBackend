package com.springboot.hospital_backend.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.springboot.hospital_backend.dto.PatientDTO;
import com.springboot.hospital_backend.models.Patient;

@Repository

public interface PatientRepository extends JpaRepository<Patient, Integer> {
    public List<Patient> findAllByOrderById();

    @Query(
    """
        SELECT new com.springboot.hospital_backend.dto.PatientDTO(p.id, p.name, p.surname, p.hasInsurance)
        FROM Patient p
        WHERE p.id = ?1
    """)
    public PatientDTO findPatientDTO(int patientId);
}
