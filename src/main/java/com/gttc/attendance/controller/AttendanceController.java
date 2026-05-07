package com.gttc.attendance.controller;

import com.gttc.attendance.model.Attendance;
import com.gttc.attendance.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.time.LocalDate;
import java.util.Optional;

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
    public Attendance markAttendance(@RequestBody Attendance attendance) {
        List<Attendance> existing = attendanceRepository.findByStudentIdAndDate(
                attendance.getStudent().getId(), attendance.getDate());
        
        if(!existing.isEmpty()) {
            Attendance primary = existing.get(0);
            primary.setStatus(attendance.getStatus());
            
            // Self-healing database mechanism: Purge legacy corrupted duplicates
            if(existing.size() > 1) {
                for(int i = 1; i < existing.size(); i++) {
                    attendanceRepository.delete(existing.get(i));
                }
            }
            return attendanceRepository.save(primary);
        } else {
            return attendanceRepository.save(attendance);
        }
    }

    @GetMapping("/by-date")
    public List<Attendance> getByDate(@RequestParam LocalDate date) {
        return attendanceRepository.findByDate(date);
    }

    @GetMapping("/student/{id}")
    public List<Attendance> getByStudent(@PathVariable Long id) {
        return attendanceRepository.findByStudentId(id);
    }
}