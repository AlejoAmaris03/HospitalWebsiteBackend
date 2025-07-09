package com.springboot.hospital_backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.springboot.hospital_backend.models.MedicalService;

@Repository

public interface MedicalServiceRepository extends JpaRepository<MedicalService, Integer>{
    public List<MedicalService> findAllByOrderById();
    public MedicalService findMedicalServiceByName(String name);
    public List<MedicalService> findAllBySpecialityId(int specialityId);

    @Query(
    """
        SELECT ms
        FROM MedicalService ms
        WHERE (ms.name != ?1) AND (ms.name = ?2)      
    """)
    public MedicalService findMedicalServiceByNameExcludingCurrentOne(String currentName, String newName);
}
