package com.lms.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lms.business.models.DummySubmissionModel;
import com.lms.persistence.entities.DummySubmissionEntity;
import com.lms.service.impl.SubmissionService;

@RestController
@RequestMapping("/submissions")
public class SubmissionController {
    private final SubmissionService service;

    public SubmissionController(SubmissionService service) {
        this.service = service;
    }

    @PostMapping("/assignment/{assignmentId}/submit")
    public String submitAssignment(@PathVariable int assignmentId, @RequestParam int studentId, @RequestBody DummySubmissionModel model) {
        if (service.submitAssignment(model, assignmentId, studentId)) {
            return "Assignment submitted successfully.";
        } else {
            return "Failed to submit assignment.";
        }
    }

    @GetMapping("/assignment/{assignmentId}")
    public List<DummySubmissionEntity> getSubmissionsByAssignment(@PathVariable("assignmentId") int assignmentId) {
        return service.getSubmissionsByAssignment(assignmentId);
    }
}
