package com.lms.service;

import com.lms.persistence.Enrollment;
import com.lms.persistence.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {
    private final List<Enrollment> enrollmentList = new ArrayList<>();
    private final UserService userService;

    public EnrollmentServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void enrollStudent(String studentId, String courseId) {
        String enrollmentId = "E" + (enrollmentList.size() + 1);
        enrollmentList.add(new Enrollment(enrollmentId, studentId, courseId));
    }

    @Override
    public List<Enrollment> getEnrollmentsByCourse(String courseId) {
        return enrollmentList.stream()
                .filter(e -> e.getcId().equals(courseId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Enrollment> getEnrollmentsByStudent(String studentId) {
        return enrollmentList.stream()
                .filter(e -> e.getsId().equals(studentId))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, List<User>> getCourseStudentMap() {
        return enrollmentList.stream()
                .collect(Collectors.groupingBy(
                        Enrollment::getcId,
                        Collectors.mapping(
                                e -> userService.findByEmail(e.getsId()).orElse(null),
                                Collectors.filtering(Objects::nonNull, Collectors.toList())
                        )
                ));
    }
}
