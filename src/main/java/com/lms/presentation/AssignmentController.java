package com.lms.presentation;

import com.lms.business.models.AssignmentModel;
import com.lms.persistence.User;
import com.lms.persistence.entities.AssignmentEntity;
import com.lms.service.impl.ServiceFacade;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course/{courseId}/assignments")
public class AssignmentController {

  private final ServiceFacade service;

  public AssignmentController(ServiceFacade service) {
    this.service = service;
  }

  @PostMapping("/create")
  public ResponseEntity<String> createAssignment(
    @PathVariable String courseId,
    @RequestBody AssignmentModel model
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

    if (service.findCourseById(courseId) == null) {
      return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body("Course not found");
    }
    AssignmentEntity entity = service.createAssignment(model, courseId);

    if (entity != null) {
      return ResponseEntity.ok(
        "Assignment " + entity.getId() + " created successfully."
      );
    } else {
      return ResponseEntity.badRequest().body("Failed to create assignment.");
    }
  }

  @GetMapping
  public ResponseEntity<Object> getAssignmentsByCourse(
    @PathVariable String courseId
  ) {
    Optional<User> currentUser = service.getCurrentUser();
    if (currentUser.isEmpty()) {
      return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body(Collections.emptyList());
    }
    if (service.findCourseById(courseId) == null) {
      return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body("Course not found");
    }
    return ResponseEntity.ok(service.getAssignmentsByCourse(courseId));
  }

  @PostMapping("/{id}/delete")
  public ResponseEntity<String> deleteAssignment(
    @PathVariable String courseId,
    @PathVariable int id
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
    if (service.findCourseById(courseId) == null) {
      return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body("Course not found");
    }
    boolean isDeleted = service.deleteAssignment(id, courseId);
    if (isDeleted) {
      return ResponseEntity.ok("Assignment status changed to 'Deleted'.");
    } else {
      return ResponseEntity
        .badRequest()
        .body("Failed to delete assignment. Assignment not found.");
    }
  }

  @PostMapping("/{id}/edit")
  public ResponseEntity<String> editAssignment(
    @PathVariable String courseId,
    @PathVariable int id,
    @RequestBody AssignmentModel model
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

    if (service.findCourseById(courseId) == null) {
      return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body("Course not found");
    }

    boolean isUpdated = service.editAssignment(id, courseId, model);
    if (isUpdated) {
      return ResponseEntity.ok("Assignment updated successfully.");
    } else {
      return ResponseEntity
        .badRequest()
        .body(
          "Failed to update assignment. Assignment not found or course mismatch."
        );
    }
  }
}
