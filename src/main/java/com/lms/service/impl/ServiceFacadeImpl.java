package com.lms.service.impl;

import com.lms.business.models.AssignmentModel;
import com.lms.business.models.AssignmentSubmissionModel;
import com.lms.persistence.*;
import com.lms.persistence.entities.*;
import com.lms.persistence.entities.questions.*;
import com.lms.service.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class ServiceFacadeImpl implements ServiceFacade {

  private final AssignmentService assignmentService;
  private final QuizServiceImpl quizService;
  private final AssignmentSubmissionServiceImpl assignmentSubmissionService;
  private final QuestionBankServiceImpl questionBankService;
  private final QuizSubmissionServiceImpl quizSubmissionService;
  private final UserService userService;
  private final CourseService courseService;
  private final EnrollmentService enrollmentService;

  // Quiz operations
  @Override
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

  @Override
  public void markQuizAsDeleted(String quizId) {
    quizService.markQuizAsDeleted(quizId);
  }

  @Override
  public void markQuizAsOpened(String quizId) {
    quizService.markQuizAsOpened(quizId);
  }

  @Override
  public List<Quiz> getAllQuizzes() {
    return quizService.getAllQuizzes();
  }

  @Override
  public Quiz getQuizById(String quizId) {
    return quizService.getQuizById(quizId);
  }

  // Assignment submission operations
  @Override
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

  @Override
  public List<AssignmentSubmissionEntity> getAssignmentSubmissionsByAssignment(
    int assignmentId
  ) {
    return assignmentSubmissionService.getSubmissionsByAssignment(assignmentId);
  }

  @Override
  public List<AssignmentSubmissionEntity> getAssignmentSubmissionsByCourse(
    String courseId
  ) {
    return assignmentSubmissionService.getSubmissionsByCourse(courseId);
  }

  @Override
  public List<AssignmentSubmissionEntity> getAssignmentSubmissionsByStudent(
    String studentId
  ) {
    return assignmentSubmissionService.getSubmissionsByStudent(studentId);
  }

  @Override
  public boolean updateSubmission(
    int submissionId,
    AssignmentSubmissionModel updatedModel
  ) {
    return assignmentSubmissionService.updateSubmission(
      submissionId,
      updatedModel
    );
  }

  @Override
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
  @Override
  public void addQuestion(String courseId, Question question) {
    questionBankService.addQuestion(courseId, question);
  }

  @Override
  public void deleteQuestion(String courseId, String questionId) {
    questionBankService.deleteQuestion(courseId, questionId);
  }

  @Override
  public List<Question> getQuestions(String courseId) {
    return questionBankService.getQuestions(courseId);
  }

  // Quiz submission operations
  @Override
  public QuizSubmission submitQuiz(
    String quizId,
    String studentId,
    Map<String, String> studentAnswers
  ) {
    return quizSubmissionService.submitQuiz(quizId, studentId, studentAnswers);
  }

  @Override
  public List<QuizSubmission> getQuizSubmissionsByStudent(String studentId) {
    return quizSubmissionService.getSubmissionsByStudent(studentId);
  }

  @Override
  public List<QuizSubmission> getAllSubmissions() {
    return quizSubmissionService.getAllSubmissions();
  }

  @Override
  public List<QuizSubmission> getQuizSubmissionsByQuiz(String quizId) {
    return quizSubmissionService.getSubmissionsByQuiz(quizId);
  }

  @Override
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
  @Override
  public boolean createAssignment(AssignmentModel model, String courseId) {
    return assignmentService.createAssignment(model, courseId);
  }

  @Override
  public boolean deleteAssignment(int id, String courseId) {
    return assignmentService.deleteAssignment(id, courseId);
  }

  @Override
  public boolean editAssignment(
    int id,
    String courseId,
    AssignmentModel model
  ) {
    return assignmentService.editAssignment(id, courseId, model);
  }

  @Override
  public List<AssignmentEntity> getAssignmentsByCourse(String courseId) {
    return assignmentService.getAssignmentsByCourse(courseId);
  }

  @Override
  public Optional<User> getCurrentUser() {
    Authentication authentication = SecurityContextHolder
      .getContext()
      .getAuthentication();
    UserDetails currentUserDetails = (UserDetails) authentication.getPrincipal();
    return userService.findByEmail(currentUserDetails.getUsername());
  }

  // Course operations
  @Override
  public void createCourse(Course course) {
    courseService.createCourse(course);
  }

  @Override
  public void uploadMedia(String courseId, MultipartFile file) {
    courseService.uploadMedia(courseId, file);
  }

  @Override
  public List<String> getMediaForCourse(String courseId) {
    return courseService.getMediaForCourse(courseId);
  }

  @Override
  public void addLessonToCourse(String courseId, Lesson lesson) {
    courseService.addLessonToCourse(courseId, lesson);
  }

  @Override
  public List<Lesson> getLessonsForCourse(String courseId) {
    return courseService.getLessonsForCourse(courseId);
  }

  @Override
  public Course findCourseById(String courseId) {
    return courseService.findCourseById(courseId);
  }

  @Override
  public List<Course> getAllCourses() {
    return courseService.getAllCourses();
  }

  // Enrollment operations
  @Override
  public void enrollStudent(String studentId, String courseId) {
    enrollmentService.enrollStudent(studentId, courseId);
  }

  @Override
  public List<Enrollment> getEnrollmentsByCourse(String courseId) {
    return enrollmentService.getEnrollmentsByCourse(courseId);
  }

  @Override
  public List<Enrollment> getEnrollmentsByStudent(String studentId) {
    return enrollmentService.getEnrollmentsByStudent(studentId);
  }

  @Override
  public Map<String, List<User>> getCourseStudentMap() {
    return enrollmentService.getCourseStudentMap();
  }
}
