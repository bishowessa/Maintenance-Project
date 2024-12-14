package com.lms.service.impl;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lms.business.models.DummySubmissionModel;
import com.lms.persistence.entities.DummySubmissionEntity;
import com.lms.persistence.repositories.SubmissionRepository;

@Service
public class SubmissionService {

  private final SubmissionRepository repository;

    public SubmissionService(SubmissionRepository repository) {
    this.repository = repository;
  }

    public boolean submitAssignment(DummySubmissionModel model, String assignmentId, String studentId) {
        DummySubmissionEntity entity = new DummySubmissionEntity();
        entity.setId(model.getId());
        entity.setStudentId(studentId);
        entity.setScore(model.getScore());
        entity.setFileURL(model.getFileURL());
        entity.setFeedback(model.getFeedback());
        entity.setRelatedId(assignmentId);
        return repository.add(entity);
    }

    public List<DummySubmissionEntity> getSubmissionsByAssignment(String assignmentId) {
        return repository.findAll().stream()
                .filter(s -> s.getRelatedId() == assignmentId)
                .collect(Collectors.toList());
    }
}
