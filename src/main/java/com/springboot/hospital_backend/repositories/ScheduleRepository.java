package com.springboot.hospital_backend.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.springboot.hospital_backend.models.Schedule;

@Repository

public interface ScheduleRepository extends JpaRepository<Schedule, Integer>{
    public List<Schedule> findAllByOrderByDayAscStartTimeAsc();

    @Query(
    """
        SELECT s
        FROM Schedule s
        WHERE (s.day = ?1) AND (s.startTime = ?2) AND (s.endTime = ?3)     
    """)
    public Schedule findSchedule(LocalDate day, LocalTime startTime, LocalTime endTime);

    @Query(
    """
        SELECT s
        FROM Schedule s
        WHERE (s.id != ?1) AND (s.day = ?2) AND (s.startTime = ?3) AND (s.endTime = ?4) 
    """)
    public Schedule findScheduleExcludingCurrentOne(int id, LocalDate day, LocalTime startTime, LocalTime endTime);

    @Query(value = 
    """
        SELECT s.*
        FROM doctors d
        JOIN doctors_schecules ds
        ON ds.doctor_id = d.id
        JOIN schedules s
        ON s.id = ds.schedule_id
        WHERE d.id = ?1 AND s.id NOT IN (
            SELECT s.id
            FROM appointments a
            WHERE a.doctor_id = d.id AND a.date = s."day" AND a.start_time = s.start_time AND a.end_time = s.end_time
        )
        ORDER BY d.id
    """, nativeQuery = true)
    public List<Schedule> findSchedulesByAvailability(int doctorId);

    @Query(value = 
    """
        SELECT s.*
        FROM doctors d
        JOIN doctors_schecules ds
        ON ds.doctor_id = d.id
        JOIN schedules s
        ON s.id = ds.schedule_id
        WHERE d.id = ?1 AND s."day" = ?2 AND s.id NOT IN (
            SELECT s.id
            FROM appointments a
            WHERE a.doctor_id = d.id AND a.date = s."day" AND a.start_time = s.start_time AND a.end_time = s.end_time
        )
        ORDER BY d.id
    """, nativeQuery = true)
    public List<Schedule> findSchedulesByDay(int doctorId, Date day);

    @Query(value = 
    """
        SELECT s.*
        FROM doctors d
        JOIN doctors_schecules ds
        ON ds.doctor_id = d.id
        JOIN schedules s
        ON s.id = ds.schedule_id
        WHERE d.id = ?1 AND s."day" = ?2
        ORDER BY d.id
    """, nativeQuery = true)
    public List<Schedule> findAllSchedulesByDay(int doctorId, Date day);
}
