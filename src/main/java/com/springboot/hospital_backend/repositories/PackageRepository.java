package com.springboot.hospital_backend.repositories;

import com.springboot.hospital_backend.models.Package;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository

public interface PackageRepository extends JpaRepository<Package, Integer> {
    public List<Package> findAllByOrderById();
    public List<Package> findAllByIncludedServicesSpecialityId(int specialityId);

    @Query
    ("""
        SELECT p
        FROM Package p
        WHERE p.id != ?1   
        ORDER BY p.id ASC     
    """)
    public List<Package> findAllExcludingCurrentOne(int packageId);
}
