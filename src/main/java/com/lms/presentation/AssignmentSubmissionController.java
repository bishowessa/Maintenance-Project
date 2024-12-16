package com.lms.presentation;

import com.lms.business.models.AssignmentSubmissionModel;
import com.lms.persistence.User;
import com.lms.persistence.entities.AssignmentSubmissionEntity;
// import com.lms.service.impl.Assignmentservice;
import com.lms.service.impl.ServiceFacade;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assignments/{assignmentId}/submissions")
public class AssignmentSubmissionController {

  private final ServiceFacade service;

  public AssignmentSubmissionController(ServiceFacade service) {
    this.service = service;
  }

  @PostMapping("/submit/{studentId}")
  public ResponseEntity<String> submitAssignment(
    @PathVariable int assignmentId,
    @PathVariable String studentId,
    @RequestBody AssignmentSubmissionModel model
  ) {
    Optional<User> currentUser = service.getCurrentUser();
    if (currentUser.isEmpty()) {
      return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body("Not authenticated");
    }
    if (!"Student".equals(currentUser.get().getRole())) {
      return ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body("Access Denied: You are unauthorized");
    }
    if (service.submitAssignment(model, assignmentId, studentId)) {
      return ResponseEntity.ok("Assignment submitted successfully.");
    } else {
      return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body("Failed to submit assignment.");
    }
  }

  @GetMapping
  public ResponseEntity<Object> getSubmissionsByAssignment(
    @PathVariable int assignmentId
  ) {
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
    List<AssignmentSubmissionEntity> submissions = service.getAssignmentSubmissionsByAssignment(
      assignmentId
    );
    return ResponseEntity.ok(submissions);
  }
}
