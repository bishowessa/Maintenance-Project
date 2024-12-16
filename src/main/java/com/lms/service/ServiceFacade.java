package com.lms.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.lms.business.models.AssignmentModel;
import com.lms.business.models.AssignmentSubmissionModel;
import com.lms.persistence.User;
import com.lms.persistence.entities.AssignmentEntity;
import com.lms.persistence.entities.AssignmentSubmissionEntity;
import com.lms.persistence.entities.Quiz;
import com.lms.persistence.entities.QuizSubmission;
import com.lms.persistence.entities.questions.Question;

public interface ServiceFacade {

  // Quiz operations
  Quiz createQuiz(
      String courseId,
      String name,
      int questionsNumber,
      int duration,
      String status);

  void markQuizAsDeleted(String quizId);

  void markQuizAsOpened(String quizId);

  List<Quiz> getAllQuizzes();

  Quiz getQuizById(String quizId);

  // Assignment submission operations
  boolean submitAssignment(
      AssignmentSubmissionModel model,
      int assignmentId,
      String studentId);

  List<AssignmentSubmissionEntity> getAssignmentSubmissionsByAssignment(
      int assignmentId);

  List<AssignmentSubmissionEntity> getAssignmentSubmissionsByCourse(
      String courseId);

  List<AssignmentSubmissionEntity> getAssignmentSubmissionsByStudent(
      String studentId);

  boolean updateSubmission(
      int submissionId,
      AssignmentSubmissionModel updatedModel);

  List<AssignmentSubmissionEntity> getAssignmentSubmissionsByStudentAndCourse(
      String studentId,
      String courseId);

  // Question bank operations
  void addQuestion(String courseId, Question question);

  void deleteQuestion(String courseId, String questionId);

  List<Question> getQuestions(String courseId);

  // Quiz submission operations
  QuizSubmission submitQuiz(
      String quizId,
      String studentId,
      Map<String, String> studentAnswers);

  List<QuizSubmission> getQuizSubmissionsByStudent(String studentId);

  List<QuizSubmission> getAllSubmissions();

  List<QuizSubmission> getQuizSubmissionsByQuiz(String quizId);

  List<QuizSubmission> getQuizSubmissionsByStudentAndCourse(
      String studentId,
      String courseId);

  // Assignment operations
  boolean createAssignment(AssignmentModel model, String courseId);

  boolean deleteAssignment(int id, String courseId);

  boolean editAssignment(
      int id,
      String courseId,
      AssignmentModel model);

  List<AssignmentEntity> getAssignmentsByCourse(String courseId);

  Optional<User> getCurrentUser();

}