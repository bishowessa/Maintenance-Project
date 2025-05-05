package com.lms.presentation;

import com.lms.business.models.AssignmentSubmissionModel;
import com.lms.events.NotificationEvent;
import com.lms.persistence.User;
import com.lms.persistence.entities.AssignmentEntity;
import com.lms.persistence.entities.AssignmentSubmissionEntity;
import com.lms.service.impl.ServiceFacade;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/assignments/{assignmentId}/submissions")
public class AssignmentSubmissionController {

    private final ServiceFacade service;
    private final ApplicationEventPublisher eventPublisher;

    private static final String UNAUTHORIZED_ACCESS = "Access Denied: You are unauthorized";
    private static final String ASSIGNMENT_NOT_FOUND = "Assignment not found";
    private static final String EMAIL = "EMAIL";

    public AssignmentSubmissionController(ServiceFacade service, ApplicationEventPublisher eventPublisher) {
        this.service = service;
        this.eventPublisher = eventPublisher;
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

        User user = currentUser.get();
        if (!"Student".equals(user.getRole())) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(UNAUTHORIZED_ACCESS);
        }

        AssignmentEntity assignment = service.findAssignmentById(assignmentId);
        if (assignment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ASSIGNMENT_NOT_FOUND);
        }

        if (service.hasStudentSubmittedAssignment(user.getId(), assignmentId)) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Assignment already submitted");
        }

        if (service.submitAssignment(model, assignmentId, user.getId())) {
            String studentMessage = "You Submitted Assignment: " + assignmentId + " Successfully";
            eventPublisher.publishEvent(new NotificationEvent(this, user.getId(), studentMessage, EMAIL));

            String instructorMessage = "Student " + user.getFirstName() + " has submitted assignment " + assignmentId;
            eventPublisher.publishEvent(new NotificationEvent(this, assignment.getInstructorId(), instructorMessage, EMAIL));

            return ResponseEntity.ok("Assignment submitted successfully.");
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Failed to submit assignment.");
        }
    }

    @GetMapping
    public ResponseEntity<Object> getSubmissionsByAssignment(@PathVariable int assignmentId) {
        Optional<User> currentUser = service.getCurrentUser();
        if (currentUser.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.emptyList());
        }

        User user = currentUser.get();
        if (!"Instructor".equals(user.getRole())) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(UNAUTHORIZED_ACCESS);
        }

        AssignmentEntity assignment = service.findAssignmentById(assignmentId);
        if (assignment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ASSIGNMENT_NOT_FOUND);
        }

        List<AssignmentSubmissionEntity> submissions = service.getAssignmentSubmissionsByAssignment(assignmentId);
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

        User user = currentUser.get();
        if (!"Instructor".equals(user.getRole())) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(UNAUTHORIZED_ACCESS);
        }

        AssignmentEntity assignmentEntity = service.findAssignmentById(assignmentId);
        if (assignmentEntity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ASSIGNMENT_NOT_FOUND);
        }

        AssignmentSubmissionEntity submission = service.getAssignmentSubmission(submissionId);
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
            String studentMessage = "Your Assignment: " + assignmentEntity.getId() + " \"" + assignmentEntity.getTitle() + "\"" +
                    " has been marked, You got score: " + submission.getScore() + " of " + assignmentEntity.getGrade() +
                    " and the feedback is : " + submission.getFeedback();
            eventPublisher.publishEvent(new NotificationEvent(this, submission.getStudentId(), studentMessage, EMAIL));

            String instructorMessage = "You marked Assignment: " + assignmentEntity.getId() + " \"" + assignmentEntity.getTitle() + "\"" +
                    " for student " + submission.getStudentId() + " successfully";
            eventPublisher.publishEvent(new NotificationEvent(this, user.getId(), instructorMessage, EMAIL));

            return ResponseEntity.ok("Assignment submission updated successfully.");
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Failed to update assignment submission.");
        }
    }
}
