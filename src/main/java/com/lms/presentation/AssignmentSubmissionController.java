package com.lms.presentation;

import com.lms.business.models.AssignmentSubmissionModel;
import com.lms.persistence.User;
import com.lms.persistence.entities.AssignmentSubmissionEntity;
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

  @PostMapping("/submit")
  public ResponseEntity<String> submitAssignment(
    @PathVariable int assignmentId,
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

    if (
      service.hasStudentSubmittedAssignment(
        currentUser.get().getId(),
        assignmentId
      )
    ) {
      return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body("Assignment already submitted");
    }

    if (
      service.submitAssignment(model, assignmentId, currentUser.get().getId())
    ) {
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

  @PatchMapping("/{submissionId}")
  public ResponseEntity<String> editAssignmentSubmission(
    @PathVariable int assignmentId,
    @PathVariable int submissionId,
    @RequestBody AssignmentSubmissionModel model
  ) {
    Optional<User> currentUser = service.getCurrentUser();
    if (currentUser.isEmpty()) {
      return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body("Not authenticated");
    }
    if (!"Instructor".equals(currentUser.get().getRole())) {
      return ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body("Access Denied: You are unauthorized");
    }

    AssignmentSubmissionEntity submission = service.getAssignmentSubmission(
      submissionId
    );
    if (submission == null) {
      return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body("Submission not found");
    }

    if (submission.getAssignmentId() != assignmentId) {
      return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body("Submission does not belong to this assignment");
    }

    submission.setFeedback(model.getFeedback());
    submission.setScore(model.getScore());
    submission.setStatus(model.getStatus());

    if (service.updateAssignmentSubmission(submission)) {
      return ResponseEntity.ok("Assignment submission updated successfully.");
    } else {
      return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body("Failed to update assignment submission.");
    }
  }
}
