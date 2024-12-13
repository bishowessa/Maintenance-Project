package com.lms.service.impl;

import com.lms.persistence.entities.AttendanceEntity;
import com.lms.persistence.repositories.AttendanceRepository;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService {
    private final AttendanceRepository repository;

    public AttendanceService(AttendanceRepository repository) {
        this.repository = repository;
    }

    public boolean createAttendance(int lessonId, String otp, int courseId) {
        AttendanceEntity entity = new AttendanceEntity();
        entity.setId(lessonId);
        entity.setLessonId(lessonId);
        entity.setOtp(otp);
        entity.setCourseId(courseId);
        return repository.add(entity);
    }

    public boolean markAttendance(int lessonId, int studentId, String otp) {
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
