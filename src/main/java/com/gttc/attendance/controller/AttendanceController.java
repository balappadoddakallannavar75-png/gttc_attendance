 package com.gttc.attendance.controller;

import com.gttc.attendance.model.Attendance;
import com.gttc.attendance.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "*")
public class AttendanceController {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @GetMapping
    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    @PostMapping
    public Attendance markAttendance(
            @RequestBody Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    @GetMapping("/student/{id}")
    public List<Attendance> getByStudent(
            @PathVariable Long id) {
        return attendanceRepository
            .findByStudentId(id);
    }
}