package com.springboot.hospital_backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.springboot.hospital_backend.models.Doctor;

@Repository

public interface DoctorRepository extends JpaRepository<Doctor, Integer>{
    public List<Doctor> findAllByOrderById();
    public List<Doctor> findDoctorsBySpecialityId(int specialityId);

    @Query(
    """
        SELECT d
        FROM Doctor d
        WHERE d.speciality.id IN ?1 
        ORDER BY d.id ASC       
    """)
    public List<Doctor> findDoctorsBySpecialities(List<Integer> specialitiesIds);
}
