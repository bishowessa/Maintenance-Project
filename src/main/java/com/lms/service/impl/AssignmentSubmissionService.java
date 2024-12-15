package com.lms.service.impl;

import com.lms.business.models.AssignmentSubmissionModel;
import com.lms.persistence.entities.AssignmentEntity;
import com.lms.persistence.entities.AssignmentSubmissionEntity;
import com.lms.persistence.repositories.RepositoryFacade;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class AssignmentSubmissionService {

  private final RepositoryFacade repository;

  public AssignmentSubmissionService(RepositoryFacade repository) {
    this.repository = repository;
  }

  public boolean submitAssignment(
    AssignmentSubmissionModel model,
    int assignmentId,
    String studentId
  ) {
    if (repository.isAssignmentExist(assignmentId)) {
      AssignmentSubmissionEntity entity = new AssignmentSubmissionEntity();
      entity.setStudentId(studentId);
      entity.setCourseId(
        repository.findAssignmentById(assignmentId).getCourseId()
      );
      entity.setFileURL(model.getFileUrl());
      entity.setRelatedId(assignmentId);
      entity.setStatus("Pending");
      return repository.addAssignmentSubmission(entity);
    } else return false;
  }

  public List<AssignmentSubmissionEntity> getSubmissionsByAssignment(
    int assignmentId
  ) {
    return repository
      .findAllAssignmentSubmissions()
      .stream()
      .filter(s -> s.getRelatedId() == assignmentId)
      .collect(Collectors.toList());
  }

  public List<AssignmentSubmissionEntity> getSubmissionsByCourse(
    String courseId
  ) {
    return repository
      .findAllAssignmentSubmissions()
      .stream()
      .filter(s -> {
        AssignmentEntity assignment = repository.findAssignmentById(
          s.getRelatedId()
        );
        return assignment != null && assignment.getCourseId() == courseId;
      })
      .collect(Collectors.toList());
  }

  public List<AssignmentSubmissionEntity> getSubmissionsByStudent(
    String studentId
  ) {
    return repository
      .findAllAssignmentSubmissions()
      .stream()
      .filter(s -> s.getStudentId() == studentId)
      .collect(Collectors.toList());
  }

  public boolean updateSubmission(
    int submissionId,
    AssignmentSubmissionModel updatedModel
  ) {
    AssignmentSubmissionEntity entity = repository.findAssignmentSubmissionById(
      submissionId
    );
    if (entity != null) {
      entity.setScore(Integer.parseInt(updatedModel.getScore()));
      entity.setFileURL(updatedModel.getFileUrl());
      entity.setFeedback(updatedModel.getFeedBack());
      return repository.updateAssignmentSubmission(entity);
    }
    return false;
  }

  public List<AssignmentSubmissionEntity> getSubmissionsByStudentAndCourse(
    String studentId,
    String courseId
  ) {
    return repository.findAssignmentSubmissionsByStudentAndCourse(
      studentId,
      courseId
    );
  }
}
