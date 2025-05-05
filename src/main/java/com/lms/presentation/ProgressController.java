package com.lms.presentation;

import com.lms.business.models.CourseProgress;
import com.lms.business.models.StudentProgress;
import com.lms.persistence.User;
import com.lms.service.impl.ServiceFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/progress")
public class ProgressController {

  private static final String INSTRUCTOR_ROLE = "Instructor";
  private static final String ACCESS_DENIED_MSG = "Access Denied: You are unauthorized";

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
    if (!INSTRUCTOR_ROLE.equals(currentUser.get().getRole())) {
      return ResponseEntity
              .status(HttpStatus.FORBIDDEN)
              .body(ACCESS_DENIED_MSG);
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
    if (!INSTRUCTOR_ROLE.equals(currentUser.get().getRole())) {
      return ResponseEntity
              .status(HttpStatus.FORBIDDEN)
              .body(ACCESS_DENIED_MSG);
    }

    if (!service.isUserExist(studentId)) {
      return ResponseEntity
              .status(HttpStatus.NOT_FOUND)
              .body("Student not found");
    }

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
    if (!INSTRUCTOR_ROLE.equals(currentUser.get().getRole())) {
      return ResponseEntity
              .status(HttpStatus.FORBIDDEN)
              .body(ACCESS_DENIED_MSG);
    }

    if (!service.isUserExist(studentId)) {
      return ResponseEntity
              .status(HttpStatus.NOT_FOUND)
              .body("Student not found");
    }

    if (service.findCourseById(courseId) == null) {
      return ResponseEntity
              .status(HttpStatus.NOT_FOUND)
              .body("Course not found");
    }

    if (service.getEnrollmentsByCourse(courseId).stream().noneMatch(enrollment -> enrollment.getsId().equals(studentId))) {
      return ResponseEntity
              .status(HttpStatus.FORBIDDEN)
              .body("Student is not enrolled in this course");
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

    if (!INSTRUCTOR_ROLE.equals(currentUser.get().getRole())) {
      return ResponseEntity
              .status(HttpStatus.FORBIDDEN)
              .body(ACCESS_DENIED_MSG);
    }

    if (service.findCourseById(courseId) == null) {
      return ResponseEntity
              .status(HttpStatus.NOT_FOUND)
              .body("Course not found");
    }

    CourseProgress courseProgress = service.getCourseProgress(courseId);
    return ResponseEntity.ok(courseProgress);
  }
}
