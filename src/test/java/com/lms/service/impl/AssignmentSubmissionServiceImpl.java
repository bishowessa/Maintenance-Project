package com.lms.service.impl;

import com.lms.business.models.AssignmentSubmissionModel;
import com.lms.persistence.entities.AssignmentEntity;
import com.lms.persistence.entities.AssignmentSubmissionEntity;
import com.lms.persistence.repositories.RepositoryFacade;

import java.util.List;

public class AssignmentSubmissionServiceImpl {

   private final RepositoryFacade repository;

   public AssignmentSubmissionServiceImpl(RepositoryFacade repository) {
      this.repository = repository;
   }

   public boolean submitAssignment(AssignmentSubmissionModel model, int assignmentId, String studentId) {
      if (!repository.isAssignmentExist(assignmentId)) return false;

      AssignmentEntity assignment = repository.findAssignmentById(assignmentId);

      AssignmentSubmissionEntity submission = new AssignmentSubmissionEntity(
              0, studentId, assignmentId, assignment.getCourseId(), 0,
              model.getFileUrl(), "", "Submitted"
      );

      return repository.addAssignmentSubmission(submission);
   }

   public List<AssignmentSubmissionEntity> getSubmissionsByAssignment(int assignmentId) {
      return repository.findAllAssignmentSubmissions().stream()
              .filter(sub -> sub.getAssignmentId() == assignmentId)
              .toList();
   }

   public boolean updateSubmission(AssignmentSubmissionEntity updatedSubmission) {
      AssignmentSubmissionEntity existing = repository.findAssignmentSubmissionById(updatedSubmission.getId());
      if (existing == null) return false;
      return repository.updateAssignmentSubmission(updatedSubmission);
   }
}
