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
    public String createAssignment(@PathVariable String courseId, @RequestBody AssignmentModel model) {
        if (service.createAssignment(model, courseId)) {
            return "Assignment created successfully.";
        } else {
            return "Failed to create assignment.";
        }
    }

    @GetMapping
    public List<AssignmentEntity> getAssignmentsByCourse(@PathVariable String courseId) {
        return service.getAssignmentsByCourse(courseId);
    }

    @PostMapping("/{id}/delete")
    public String deleteAssignment(@PathVariable String courseId, @PathVariable int id) {
        boolean isDeleted = service.deleteAssignment(id, courseId);
        if (isDeleted) {
            return "Assignment status changed to 'Deleted'.";
        } else {
            return "Failed to delete assignment. Assignment not found.";
        }
    }

    @PostMapping("/{id}/edit")
    public String editAssignment(
            @PathVariable String courseId,
            @PathVariable int id,
            @RequestBody AssignmentModel model) {

        boolean isUpdated = service.editAssignment(id, courseId, model);

        if (isUpdated) {
            return "Assignment updated successfully.";
        } else {
            return "Failed to update assignment. Assignment not found or course mismatch.";
        }
    }
}
