package com.lms.service.impl;

import com.lms.business.models.AssignmentModel;
import com.lms.persistence.entities.AssignmentEntity;
import com.lms.persistence.repositories.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssignmentService {
    private final Repository<AssignmentEntity> repository;

    public AssignmentService(Repository<AssignmentEntity> repository) {
        this.repository = repository;
    }

    public boolean createAssignment(AssignmentModel model, int courseId) {
        AssignmentEntity entity = new AssignmentEntity();
        entity.setId(model.getId());
        entity.setDescription(model.getDescription());
        entity.setSubmissionURL(model.getSubmissionURL());
        entity.setGrade(model.getGrade());
        entity.setCourseId(courseId);
        return repository.add(entity);
    }

    public List<AssignmentEntity> getAssignmentsByCourse(int courseId) {
        return repository.findAll().stream()
                .filter(a -> a.getCourseId() == courseId)
                .collect(Collectors.toList());
    }
}
