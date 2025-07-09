package com.springboot.hospital_backend.services;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springboot.hospital_backend.models.Schedule;
import com.springboot.hospital_backend.repositories.ScheduleRepository;

@Service

public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepo;

    public List<Schedule> getSchedules() {
        return this.scheduleRepo.findAllByOrderByDayAscStartTimeAsc();
    }

    public Schedule getScheduleById(int id) {
        return this.scheduleRepo.findById(id).get();
    }

    public List<Schedule> getSchedulesByAvailability(int doctorId) {
        return this.scheduleRepo.findSchedulesByAvailability(doctorId);
    }

    public List<Schedule> getSchedulesByDay(int doctorId, Date day) {
        return this.scheduleRepo.findSchedulesByDay(doctorId, day);
    }

    public List<Schedule> getAllSchedulesByDay(int doctorId, Date day) {
        return this.scheduleRepo.findAllSchedulesByDay(doctorId, day);
    }

    public Schedule saveSchedule(Schedule schedule, String action) {
        switch (action) {
            case "add": {
                if(this.scheduleRepo.findSchedule(schedule.getDay(), 
                    schedule.getStartTime(), schedule.getEndTime()) == null)
                    return this.scheduleRepo.save(schedule);

                break;
            }
        
            case "update": {
                Schedule currentSchedule = this.scheduleRepo.findById(schedule.getId()).get();

                if(this.scheduleRepo.findScheduleExcludingCurrentOne(currentSchedule.getId(), 
                    schedule.getDay(), schedule.getStartTime(), schedule.getEndTime()) == null)
                    return this.scheduleRepo.save(schedule);

                break;
            }
        }

        return null;
    }

    public void deleteSchedule(int id) {
        this.scheduleRepo.deleteById(id);
    }
}
