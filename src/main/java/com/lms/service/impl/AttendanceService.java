package com.lms.service.impl;

import com.lms.persistence.entities.AttendanceEntity;
import com.lms.persistence.repositories.AttendanceRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AttendanceService {
    private final AttendanceRepository repository;

    public AttendanceService(AttendanceRepository repository) {
        this.repository = repository;
    }

    public boolean createAttendance(String lessonId, String otp, String courseId) {
        List<String> Ids = new ArrayList<>();
        String id = "";
        AttendanceEntity entity = new AttendanceEntity(id,lessonId,courseId,otp,Ids);
        return repository.add(entity);
    }

    public boolean markAttendance(String lessonId, String studentId, String otp) {
        AttendanceEntity attendance = repository.findAll().stream()
                .filter(a -> a.getLessonId() == lessonId && a.getOtp().equals(otp))
                .findFirst().orElse(null);
        if (attendance != null) {
            attendance.getStudentIds().add(studentId);
            return true;
        }
        return false;
    }

}
