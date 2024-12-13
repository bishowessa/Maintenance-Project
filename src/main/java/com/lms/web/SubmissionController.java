package com.lms.web;

import com.lms.business.models.SubmissionModel;
import com.lms.persistence.entities.SubmissionEntity;
import com.lms.service.impl.SubmissionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/submissions")
public class SubmissionController {
    private final SubmissionService service;

    public SubmissionController(SubmissionService service) {
        this.service = service;
    }

    @PostMapping("/assignment/{assignmentId}/submit")
    public String submitAssignment(@PathVariable int assignmentId, @RequestParam int studentId, @RequestBody SubmissionModel model) {
        if (service.submitAssignment(model, assignmentId, studentId)) {
            return "Assignment submitted successfully.";
        } else {
            return "Failed to submit assignment.";
        }
    }

    @GetMapping("/assignment/{assignmentId}")
    public List<SubmissionEntity> getSubmissionsByAssignment(@PathVariable int assignmentId) {
        return service.getSubmissionsByAssignment(assignmentId);
    }
}
