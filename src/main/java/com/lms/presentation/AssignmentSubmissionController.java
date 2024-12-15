package com.lms.presentation;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lms.business.models.AssignmentSubmissionModel;
import com.lms.persistence.entities.AssignmentSubmissionEntity;
// import com.lms.service.impl.Assignmentservice;
import com.lms.service.impl.ServiceFacade;

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
            @RequestBody AssignmentSubmissionModel model) {

        if (service.submitAssignment(model, assignmentId, studentId)) {
            return ResponseEntity.ok("Assignment submitted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to submit assignment.");
        }
    }

    @GetMapping
    public ResponseEntity<List<AssignmentSubmissionEntity>> getSubmissionsByAssignment(@PathVariable int assignmentId) {
        List<AssignmentSubmissionEntity> submissions = service.getAssignmentSubmissionsByAssignment(assignmentId);
        return ResponseEntity.ok(submissions);
    }
}