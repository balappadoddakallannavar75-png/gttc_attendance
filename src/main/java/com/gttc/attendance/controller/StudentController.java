package com.gttc.attendance.controller;

import com.gttc.attendance.model.Student;
import com.gttc.attendance.model.Attendance;
import com.gttc.attendance.repository.StudentRepository;
import com.gttc.attendance.repository.AttendanceRepository;
import com.gttc.attendance.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmailService emailService;

    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @PostMapping
    public Student addStudent(
            @RequestBody Student student) {
        return studentRepository.save(student);
    }

    @org.springframework.transaction.annotation.Transactional
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        List<Attendance> attendances = attendanceRepository.findByStudentId(id);
        if (!attendances.isEmpty()) {
            attendanceRepository.deleteAll(attendances);
        }
        studentRepository.deleteById(id);
    }

    @PostMapping("/{id}/check-eligibility")
    public String checkEligibilityAndSendEmail(@PathVariable Long id) {
        Student student = studentRepository.findById(id).orElse(null);
        if (student == null) return "Student not found";

        List<Attendance> attendances = attendanceRepository.findByStudentId(id);
        if (attendances.isEmpty()) return "No attendance records found for " + student.getName();

        long total = attendances.size();
        long present = attendances.stream().filter(a -> "Present".equalsIgnoreCase(a.getStatus())).count();

        double percentage = ((double) present / total) * 100;
        String subject = "GTTC Exam Authority - Official Eligibility Notification";
        String body;

        String header = "=================================================\n" +
                        "     GTTC EXAM AUTHORITY - OFFICIAL NOTICE \n" +
                        "=================================================\n\n" +
                        "Dear " + student.getName() + " (" + student.getRollNumber() + "),\n\n" +
                        "This is an official communication from the GTTC Administration regarding your examination eligibility status.\n\n";

        String detailsStr = "Calculated Attendance: " + present + " Present Days / " + total + " Total Classes Logged\n" +
                            "Final Percentage: " + String.format("%.2f", percentage) + "%\n\n";

        String footer = "\n\nPlease contact the administration office immediately if you believe this computational framework calculation is in error.\n\n" +
                        "Sincerely,\n" +
                        "GTTC Principal Office / Management Dept.";

        if (percentage >= 80.0) {
            body = header + detailsStr + "STATUS: APPROVED ✅\nCongratulations, you have met the 80% minimum attendance threshold. You are fully authorized to sit for the upcoming examinations." + footer;
        } else {
            body = header + detailsStr + "STATUS: REJECTED ❌\nUnfortunately, as per strict GTTC academic mandates, you have failed to meet the 80% minimum attendance threshold and are hereby DEBARRED from the upcoming examinations." + footer;
        }

        String details = "Calculation: " + present + " Present Days / " + total + " Total Classes * 100 = (" + String.format("%.2f", percentage) + "%). ";

        if (student.getEmail() != null && !student.getEmail().trim().isEmpty()) {
            emailService.sendEmail(student.getEmail(), subject, body);
            return (percentage >= 80.0 ? "✅ ELIGIBLE: " : "❌ REJECTED: ") + details + "Official letter dispatched.";
        } else {
            return (percentage >= 80.0 ? "✅ ELIGIBLE: " : "❌ REJECTED: ") + details + "Warning: Student has no email on file!";
        }
    }
}