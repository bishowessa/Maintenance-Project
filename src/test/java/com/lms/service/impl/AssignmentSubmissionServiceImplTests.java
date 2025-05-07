package com.lms.service.impl;

import com.lms.business.models.AssignmentSubmissionModel;
import com.lms.persistence.entities.AssignmentEntity;
import com.lms.persistence.entities.AssignmentSubmissionEntity;
import com.lms.persistence.repositories.RepositoryFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AssignmentSubmissionServiceImplTests {

   @Mock
   private RepositoryFacade repository;

   private AssignmentSubmissionServiceImpl service;

   private final String studentId = "S01";
   private final int assignmentId = 1;

   private AssignmentSubmissionModel model;
   private AssignmentSubmissionEntity assignmentSubmission;

   @BeforeEach
   void setup() {
      MockitoAnnotations.openMocks(this);
      service = new AssignmentSubmissionServiceImpl(repository);

      String fileUrl = "fileUrl";
      model = new AssignmentSubmissionModel(studentId, fileUrl, null, null, null);

      assignmentSubmission = new AssignmentSubmissionEntity(
              1, studentId, assignmentId, "C01", 5, fileUrl, "feedback", "Marked"
      );
   }

   @Test
   void testSubmitAssignment_Succeeds() {
      AssignmentEntity assignment = mock(AssignmentEntity.class);

      when(repository.isAssignmentExist(assignmentId)).thenReturn(true);
      when(repository.findAssignmentById(assignmentId)).thenReturn(assignment);
      when(repository.addAssignmentSubmission(any())).thenReturn(true);

      boolean result = service.submitAssignment(model, assignmentId, studentId);

      assertTrue(result);
      verify(repository).addAssignmentSubmission(any(AssignmentSubmissionEntity.class));
   }

   @Test
   void testSubmitAssignment_Fails_IfAssignmentNotFound() {
      when(repository.isAssignmentExist(assignmentId)).thenReturn(false);

      boolean result = service.submitAssignment(model, assignmentId, studentId);

      assertFalse(result);
      verify(repository, never()).addAssignmentSubmission(any());
   }

   @Test
   void testGetSubmissionsByAssignment() {
      when(repository.findAllAssignmentSubmissions()).thenReturn(List.of(assignmentSubmission));

      var submissions = service.getSubmissionsByAssignment(assignmentId);

      assertNotNull(submissions);
      assertFalse(submissions.isEmpty());
      assertEquals(1, submissions.size());
   }

   @Test
   void testUpdateSubmission_Succeeds() {
      AssignmentSubmissionEntity submission = new AssignmentSubmissionEntity(
              1, studentId, assignmentId, "C01", 5, "fileUrl", "feedback", "Marked"
      );

      when(repository.findAssignmentSubmissionById(1)).thenReturn(submission);
      when(repository.updateAssignmentSubmission(submission)).thenReturn(true);

      boolean result = service.updateSubmission(submission);

      assertTrue(result);
      verify(repository).updateAssignmentSubmission(submission);
   }

   @Test
   void testUpdateSubmission_Fails() {
      AssignmentSubmissionEntity submission = new AssignmentSubmissionEntity(
              1, studentId, assignmentId, "C01", 5, "fileUrl", "feedback", "Marked"
      );

      when(repository.findAssignmentSubmissionById(1)).thenReturn(null);

      boolean result = service.updateSubmission(submission);

      assertFalse(result);
      verify(repository, never()).updateAssignmentSubmission(submission);
   }
}
