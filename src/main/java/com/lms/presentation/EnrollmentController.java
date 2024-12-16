package com.lms.presentation;

import com.lms.persistence.Enrollment;
import com.lms.persistence.User;
import com.lms.service.EnrollmentService;
import com.lms.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    private final UserService userService;
    public EnrollmentController(EnrollmentService enrollmentService, UserService userService) {
        this.enrollmentService = enrollmentService;
        this.userService = userService;
    }

    @PostMapping("/enroll")
    public ResponseEntity<String> enrollStudent(@RequestParam String studentId, @RequestParam String courseId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails currentUserDetails = (UserDetails) authentication.getPrincipal();
        Optional<User> currentUser = userService.findByEmail(currentUserDetails.getUsername());

        if (currentUser.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        if (!"Student".equals(currentUser.get().getRole())) {
            return ResponseEntity.status(403).body("Access Denied: Access Denied: you are unauthorized");
        }
        enrollmentService.enrollStudent(studentId, courseId);
        return ResponseEntity.ok("Student enrolled successfully");
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<String> getEnrollmentsByCourse(@PathVariable String courseId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails currentUserDetails = (UserDetails) authentication.getPrincipal();
        Optional<User> currentUser = userService.findByEmail(currentUserDetails.getUsername());
        if (currentUser.isEmpty()) {
            return ResponseEntity.status(404).build();
        }


        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByCourse(courseId);
        return ResponseEntity.ok(enrollments.toString());
    }

}
