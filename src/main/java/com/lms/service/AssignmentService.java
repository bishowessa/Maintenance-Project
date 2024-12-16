package com.lms.service;

import java.util.List;

import com.lms.business.models.AssignmentModel;
import com.lms.persistence.entities.AssignmentEntity;

public interface AssignmentService {

  boolean createAssignment(AssignmentModel model, String courseId);

  boolean deleteAssignment(int id, String courseId);

  boolean editAssignment(
      int id,
      String courseId,
      AssignmentModel model);

  List<AssignmentEntity> getAssignmentsByCourse(String courseId);

}