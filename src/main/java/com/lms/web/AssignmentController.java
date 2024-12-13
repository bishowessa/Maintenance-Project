package com.lms.web;

import com.lms.business.models.AssignmentModel;
import com.lms.persistence.entities.AssignmentEntity;
import com.lms.service.impl.AssignmentService;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/course/{courseId}/assignments")
class AssignmentController {
    private final AssignmentService service;

    public AssignmentController(AssignmentService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public String createAssignment(@PathVariable int courseId, @RequestBody AssignmentModel model) {
        if (service.createAssignment(model, courseId)) {
            return "Assignment created successfully.";
        } else {
            return "Failed to create assignment.";
        }
    }

    @GetMapping
    public List<AssignmentEntity> getAssignmentsByCourse(@PathVariable int courseId) {
        return service.getAssignmentsByCourse(courseId);
    }
}
