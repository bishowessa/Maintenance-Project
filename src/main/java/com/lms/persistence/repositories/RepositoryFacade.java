package com.lms.persistence.repositories;

import com.lms.persistence.entities.AssignmentEntity;
import com.lms.persistence.entities.AssignmentSubmissionEntity;
import com.lms.persistence.entities.QuestionBank;
import com.lms.persistence.entities.Quiz;
import com.lms.persistence.entities.QuizSubmission;
import java.util.List;
import java.util.Optional;

public interface RepositoryFacade {
  // Assignment operations
  boolean addAssignment(AssignmentEntity entity);

  List<AssignmentEntity> findAllAssignments();

  AssignmentEntity findAssignmentById(int id);

  // Assignment Submission operations
  boolean addAssignmentSubmission(AssignmentSubmissionEntity submission);

  List<AssignmentSubmissionEntity> findAllAssignmentSubmissions();

  AssignmentSubmissionEntity findAssignmentSubmissionById(int id);

  List<AssignmentSubmissionEntity> findAssignmentSubmissionsByStudentAndCourse(
    String studentId,
    String courseId
  );

  // Question Bank operations
  void saveQuestionBank(QuestionBank questionBank);

  QuestionBank findQuestionBankByCourseId(String courseId);

  // Quiz operations
  void saveQuiz(Quiz quiz);

  Optional<Quiz> findQuizById(String quizId);

  List<Quiz> findAllQuizzes();

  // Quiz Submission operations
  void saveQuizSubmission(QuizSubmission submission);

  List<QuizSubmission> findAllQuizSubmissions();

  List<QuizSubmission> findQuizSubmissionsByStudentId(String studentId);

  List<QuizSubmission> findQuizSubmissionsByQuizId(String quizId);

  List<QuizSubmission> findQuizSubmissionsByStudentAndCourse(
    String studentId,
    String courseId
  );

  boolean isAssignmentExist(int assignmentId);

  boolean updateAssignmentSubmission(AssignmentSubmissionEntity entity);

  void updateQuiz(Quiz quiz);
}
