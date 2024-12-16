package com.lms.service.impl;

import com.lms.business.models.AssignmentModel;
import com.lms.business.models.AssignmentSubmissionModel;
import com.lms.persistence.User;
import com.lms.persistence.entities.AssignmentEntity;
import com.lms.persistence.entities.AssignmentSubmissionEntity;
import com.lms.persistence.entities.Quiz;
import com.lms.persistence.entities.QuizSubmission;
import com.lms.persistence.entities.questions.Question;
import com.lms.service.UserService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ServiceFacade {

  private final AssignmentServiceImpl assignmentService;
  private final QuizServiceImpl quizService;
  private final AssignmentSubmissionServiceImpl assignmentSubmissionService;
  private final QuestionBankServiceImpl questionBankService;
  private final QuizSubmissionServiceImpl quizSubmissionService;
  private final UserService userService;

  // Quiz operations
  public Quiz createQuiz(
    String courseId,
    String name,
    int questionsNumber,
    int duration,
    String status
  ) {
    return quizService.createQuiz(
      courseId,
      name,
      questionsNumber,
      duration,
      status
    );
  }

  public void markQuizAsDeleted(String quizId) {
    quizService.markQuizAsDeleted(quizId);
  }

  public void markQuizAsOpened(String quizId) {
    quizService.markQuizAsOpened(quizId);
  }

  public List<Quiz> getAllQuizzes() {
    return quizService.getAllQuizzes();
  }

  public Quiz getQuizById(String quizId) {
    return quizService.getQuizById(quizId);
  }

  // Assignment submission operations
  public boolean submitAssignment(
    AssignmentSubmissionModel model,
    int assignmentId,
    String studentId
  ) {
    return assignmentSubmissionService.submitAssignment(
      model,
      assignmentId,
      studentId
    );
  }

  public List<AssignmentSubmissionEntity> getAssignmentSubmissionsByAssignment(
    int assignmentId
  ) {
    return assignmentSubmissionService.getSubmissionsByAssignment(assignmentId);
  }

  public List<AssignmentSubmissionEntity> getAssignmentSubmissionsByCourse(
    String courseId
  ) {
    return assignmentSubmissionService.getSubmissionsByCourse(courseId);
  }

  public List<AssignmentSubmissionEntity> getAssignmentSubmissionsByStudent(
    String studentId
  ) {
    return assignmentSubmissionService.getSubmissionsByStudent(studentId);
  }

  public boolean updateSubmission(
    int submissionId,
    AssignmentSubmissionModel updatedModel
  ) {
    return assignmentSubmissionService.updateSubmission(
      submissionId,
      updatedModel
    );
  }

  public List<AssignmentSubmissionEntity> getAssignmentSubmissionsByStudentAndCourse(
    String studentId,
    String courseId
  ) {
    return assignmentSubmissionService.getSubmissionsByStudentAndCourse(
      studentId,
      courseId
    );
  }

  // Question bank operations
  public void addQuestion(String courseId, Question question) {
    questionBankService.addQuestion(courseId, question);
  }

  public void deleteQuestion(String courseId, String questionId) {
    questionBankService.deleteQuestion(courseId, questionId);
  }

  public List<Question> getQuestions(String courseId) {
    return questionBankService.getQuestions(courseId);
  }

  // Quiz submission operations
  public QuizSubmission submitQuiz(
    String quizId,
    String studentId,
    Map<String, String> studentAnswers
  ) {
    return quizSubmissionService.submitQuiz(quizId, studentId, studentAnswers);
  }

  public List<QuizSubmission> getQuizSubmissionsByStudent(String studentId) {
    return quizSubmissionService.getSubmissionsByStudent(studentId);
  }

  public List<QuizSubmission> getAllSubmissions() {
    return quizSubmissionService.getAllSubmissions();
  }

  public List<QuizSubmission> getQuizSubmissionsByQuiz(String quizId) {
    return quizSubmissionService.getSubmissionsByQuiz(quizId);
  }

  public List<QuizSubmission> getQuizSubmissionsByStudentAndCourse(
    String studentId,
    String courseId
  ) {
    return quizSubmissionService.getSubmissionsByStudentAndCourse(
      studentId,
      courseId
    );
  }

  // Assignment operations
  public boolean createAssignment(AssignmentModel model, String courseId) {
    return assignmentService.createAssignment(model, courseId);
  }

  public boolean deleteAssignment(int id, String courseId) {
    return assignmentService.deleteAssignment(id, courseId);
  }

  public boolean editAssignment(
    int id,
    String courseId,
    AssignmentModel model
  ) {
    return assignmentService.editAssignment(id, courseId, model);
  }

  public List<AssignmentEntity> getAssignmentsByCourse(String courseId) {
    return assignmentService.getAssignmentsByCourse(courseId);
  }

  public Optional<User> getCurrentUser() {
    Authentication authentication = SecurityContextHolder
      .getContext()
      .getAuthentication();
    UserDetails currentUserDetails = (UserDetails) authentication.getPrincipal();
    return userService.findByEmail(currentUserDetails.getUsername());
  }
}
