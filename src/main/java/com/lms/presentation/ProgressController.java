package com.lms.presentation;

import com.lms.business.models.CourseProgress;
import com.lms.business.models.StudentProgress;
import com.lms.persistence.User;
import com.lms.service.impl.ServiceFacade;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/progress")
public class ProgressController {

  private final ServiceFacade service;

  public ProgressController(ServiceFacade service) {
    this.service = service;
  }

  // Get all student progress
  @GetMapping("/students")
  public ResponseEntity<Object> getAllStudentProgress() {
    Optional<User> currentUser = service.getCurrentUser();
    if (currentUser.isEmpty()) {
      return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body(Collections.emptyList());
    }
    if (!"Instructor".equals(currentUser.get().getRole())) {
      return ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body("Access Denied: You are unauthorized");
    }
    List<StudentProgress> studentsProgresses = service.getAllStudentProgress();
    return ResponseEntity.ok(studentsProgresses);
  }

  // Get student progress by studentId
  @GetMapping("/students/{studentId}")
  public ResponseEntity<Object> getStudentProgressByCourseId(
    @PathVariable String studentId
  ) {
    Optional<User> currentUser = service.getCurrentUser();
    if (currentUser.isEmpty()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
    if (!"Instructor".equals(currentUser.get().getRole())) {
      return ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body("Access Denied: You are unauthorized");
    }

    // check if studentId is exist
    /////////////////////////////////////////////////////
    // service.studentExistsById()

    StudentProgress studentProgress = service.getStudentProgressByStudentId(
      studentId
    );
    return ResponseEntity.ok(studentProgress);
  }

  // Get student progress by courseId
  @GetMapping("/students/{studentId}/{courseId}")
  public ResponseEntity<Object> getStudentProgressByCourseId(
    @PathVariable String studentId,
    @PathVariable String courseId
  ) {
    Optional<User> currentUser = service.getCurrentUser();
    if (currentUser.isEmpty()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
    if (!"Instructor".equals(currentUser.get().getRole())) {
      return ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body("Access Denied: You are unauthorized");
    }

    StudentProgress studentProgress = service.getStudentProgressByStudentIdAndCourseId(
      studentId,
      courseId
    );
    return ResponseEntity.ok(studentProgress);
  }

  // Get course progress
  @GetMapping("/courses/{courseId}")
  public ResponseEntity<Object> getCourseProgress(
    @PathVariable String courseId
  ) {
    Optional<User> currentUser = service.getCurrentUser();
    if (currentUser.isEmpty()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
    if (!"Instructor".equals(currentUser.get().getRole())) {
      return ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body("Access Denied: You are unauthorized");
    }
    CourseProgress courseProgress = service.getCourseProgress(courseId);
    return ResponseEntity.ok(courseProgress);
  }
}
