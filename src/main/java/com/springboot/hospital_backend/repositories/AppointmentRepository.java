package com.springboot.hospital_backend.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.springboot.hospital_backend.models.Appointment;

@Repository

public interface AppointmentRepository extends JpaRepository<Appointment, Integer>{
    public List<Appointment> findAllByOrderByDateAscStartTimeAsc();
    public List<Appointment> findAppointmentsByPatientIdOrderByDateAscStartTimeAsc(int patientId);
    public List<Appointment> findAppointmentsByDoctorIdOrderByDateAscStartTimeAsc(int doctorId);
    public Appointment findAppointmentById(int appointmentId);
    public List<Appointment> findAppointmentsByMedicalServiceId(int serviceId);
    public List<Appointment> findAppointmentsByMedicalPackageId(int packageId);

    @Query(value = 
    """
        SELECT a.*
        FROM packages p
        JOIN services_packages sp
        ON p.id = sp.package_id
        JOIN appointments a
        ON p.id = a.package_id
        WHERE sp.service_id = ?1
        GROUP BY a.id
        ORDER BY a.id ASC     
    """, nativeQuery = true)
    public List<Appointment> findAppointmentsByPackagesContaintService(int serviceId);

    @Query(value = 
    """
            SELECT * 
            FROM appointments a
            WHERE 
                a.patient_id = ?1 AND a.date = ?2 AND 
                (CAST(a.start_time AS time) < CAST(?3 AS time) AND CAST(a.end_time AS time) > CAST(?4 AS time))
    """, nativeQuery = true)
    public Appointment checkValidSchedule(int patientId, LocalDate date, LocalTime endTime, LocalTime startTime);

    @Query(value = 
    """
            SELECT * 
            FROM appointments a
            WHERE 
                a.id != ?1 AND a.patient_id = ?2 AND a.date = ?3 AND 
                (CAST(a.start_time AS time) < CAST(?4 AS time) AND CAST(a.end_time AS time) > CAST(?5 AS time))
    """, nativeQuery = true)
    public Appointment checkValidSchedule(int appointmentId, int patientId, LocalDate date, LocalTime endTime, LocalTime startTime);

    @Query(value = 
    """
            SELECT * 
            FROM appointments a
            WHERE a.patient_id = ?1 AND a.service_id = ?2 AND a.date >= CURRENT_DATE
    """, nativeQuery = true)
    public Appointment checkService(int patientId, int serviceId);

    @Query(value = 
    """
            SELECT * 
            FROM appointments a
            WHERE a.id != ?1 AND a.patient_id = ?2 AND a.service_id = ?3 AND a.date >= CURRENT_DATE
    """, nativeQuery = true)
    public Appointment checkService(int appointmentId, int patientId, int serviceId);

    @Query(value = 
    """
            SELECT * 
            FROM appointments a
            WHERE a.patient_id = ?1 AND a.package_id = ?2 AND a.date >= CURRENT_DATE
    """, nativeQuery = true)
    public Appointment checkPackage(int patientId, int packageId);

    @Query(value = 
    """
            SELECT * 
            FROM appointments a
            WHERE a.id != ?1 AND a.patient_id = ?2 AND a.package_id = ?3 AND a.date >= CURRENT_DATE
    """, nativeQuery = true)
    public Appointment checkPackage(int appointmentId, int patientId, int packageId);

    @Query(value = 
    """
        SELECT *
        FROM appointments a
        WHERE 
            a.id = ?1 AND CURRENT_DATE = a.date AND (a.start_time - INTERVAL '2 hours' < CURRENT_TIME)         
    """, nativeQuery = true)
    public Appointment checkAppointmentTimeToCancel(int appointmenId);

    @Modifying
    @Query(
     """
        DELETE FROM Appointment WHERE id = ?1           
     """)
    public void deleteAppointmentById(int appointmentId);
}
