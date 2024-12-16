package com.lms.service;

import java.util.List;

import com.lms.business.models.AssignmentSubmissionModel;
import com.lms.persistence.entities.AssignmentSubmissionEntity;

public interface AssignmentSubmissionService {

  boolean submitAssignment(
      AssignmentSubmissionModel model,
      int assignmentId,
      String studentId);

  List<AssignmentSubmissionEntity> getSubmissionsByAssignment(
      int assignmentId);

  List<AssignmentSubmissionEntity> getSubmissionsByCourse(
      String courseId);

  List<AssignmentSubmissionEntity> getSubmissionsByStudent(
      String studentId);

  boolean updateSubmission(
      int submissionId,
      AssignmentSubmissionModel updatedModel);

  List<AssignmentSubmissionEntity> getSubmissionsByStudentAndCourse(
      String studentId,
      String courseId);

}