package com.lms.service.impl;

import com.lms.business.models.SubmissionModel;
import com.lms.persistence.entities.SubmissionEntity;
import com.lms.persistence.repositories.SubmissionRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class SubmissionService {

  private final SubmissionRepository repository;

  public SubmissionService(SubmissionRepository repository) {
    this.repository = repository;
  }

  public boolean submitAssignment(
    SubmissionModel model,
    int assignmentId,
    int studentId
  ) {
    int id = 0;
    SubmissionEntity entity = new SubmissionEntity(
      id,
      studentId,
      model.getScore(),
      model.getFileURL(),
      model.getFeedback(),
      assignmentId
    );
    return repository.add(entity);
  }

  public List<SubmissionEntity> getSubmissionsByAssignment(int assignmentId) {
    return repository
      .findAll()
      .stream()
      .filter(s -> s.getRelatedId() == assignmentId)
      .collect(Collectors.toList());
  }
}
