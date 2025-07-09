package com.springboot.hospital_backend.controllers;

import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.hospital_backend.models.Schedule;
import com.springboot.hospital_backend.services.ScheduleService;

@RestController
@RequestMapping("/schedules")

public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<List<Schedule>> getSchedules() {
        return ResponseEntity.ok(this.scheduleService.getSchedules());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable int id) {
        return ResponseEntity.ok(this.scheduleService.getScheduleById(id));
    }

    @GetMapping("/find/availavility/{doctorId}")
    public List<Schedule> getSchedulesByAvailability(@PathVariable int doctorId) {
        return this.scheduleService.getSchedulesByAvailability(doctorId);
    }

    @GetMapping("/find/times/{doctorId}/{day}")
    public List<Schedule> getSchedulesByDay(@PathVariable int doctorId, @PathVariable Date day) {
        return this.scheduleService.getSchedulesByDay(doctorId, day);
    }

    @GetMapping("/find/allTimes/{doctorId}/{day}")
    public List<Schedule> getAllSchedulesByDay(@PathVariable int doctorId, @PathVariable Date day) {
        return this.scheduleService.getAllSchedulesByDay(doctorId, day);
    }

    @PostMapping("/save")
    public ResponseEntity<Schedule> saveSchedule(@RequestPart Schedule schedule, @RequestPart String action) {
        return ResponseEntity.ok(this.scheduleService.saveSchedule(schedule, action));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable int id) {
        this.scheduleService.deleteSchedule(id);
        return ResponseEntity.ok().body(null);
    }
}
