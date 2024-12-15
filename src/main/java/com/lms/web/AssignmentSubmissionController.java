package com.lms.web;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.lms.business.models.AssignmentSubmissionModel;
import com.lms.persistence.entities.AssignmentSubmissionEntity;
import com.lms.service.impl.AssignmentSubmissionService;

@RestController
@RequestMapping("/assignments/{assignmentId}/submissions")
public class AssignmentSubmissionController {

    private final AssignmentSubmissionService submissionService;

    public AssignmentSubmissionController(AssignmentSubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @PostMapping("/submit/{studentId}")
    public ResponseEntity<String> submitAssignment(
            @PathVariable int assignmentId,
            @PathVariable String studentId,
            @RequestBody AssignmentSubmissionModel model) {

        if (submissionService.submitAssignment(model, assignmentId, studentId)) {
            return ResponseEntity.ok("Assignment submitted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to submit assignment.");
        }
    }

    @GetMapping
    public ResponseEntity<List<AssignmentSubmissionEntity>> getSubmissionsByAssignment(@PathVariable int assignmentId) {
        List<AssignmentSubmissionEntity> submissions = submissionService.getSubmissionsByAssignment(assignmentId);
        return ResponseEntity.ok(submissions);
    }
}