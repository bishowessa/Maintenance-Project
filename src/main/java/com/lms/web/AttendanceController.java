package com.lms.web;

import com.lms.service.impl.AttendanceService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course/{courseId}/attendance")
public class AttendanceController {
    private final AttendanceService service;

    public AttendanceController(AttendanceService service) {
        this.service = service;
    }

    @PostMapping("/lesson/{lessonId}/create")
    public String createAttendance(@PathVariable String courseId, @PathVariable String lessonId, @RequestParam String otp) {
        if (service.createAttendance(lessonId, otp, courseId)) {
            return "Attendance created successfully.";
        } else {
            return "Failed to create attendance.";
        }
    }

    @PostMapping("/lesson/{lessonId}/mark")
    public String markAttendance(@PathVariable String courseId, @PathVariable String lessonId, @RequestParam String studentId, @RequestParam String otp) {
        if (service.markAttendance(lessonId, studentId, otp)) {
            return "Attendance marked successfully.";
        } else {
            return "Failed to mark attendance.";
        }
    }
}
