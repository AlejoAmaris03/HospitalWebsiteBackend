package com.springboot.hospital_backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.springboot.hospital_backend.models.Speciality;

@Repository

public interface SpecialityRepository extends JpaRepository<Speciality, Integer>{
    public List<Speciality> findAllByOrderById();
    public Speciality findSpecialityByName(String name);

    @Query(
    """
        SELECT s
        FROM Speciality s
        WHERE (s.name != ?1) AND (s.name = ?2)      
    """)
    public Speciality findSpecialityByNameExcludingCurrentOne(String currentName, String newName);
}
