package com.lms.service.impl;

import com.lms.business.models.AssignmentModel;
import com.lms.business.models.AssignmentSubmissionModel;
import com.lms.business.models.CourseProgress;
import com.lms.business.models.StudentProgress;
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
public class ServiceFacade {

  private final AssignmentService assignmentService;
  private final QuizServiceImpl quizService;
  private final AssignmentSubmissionServiceImpl assignmentSubmissionService;
  private final QuestionBankServiceImpl questionBankService;
  private final QuizSubmissionServiceImpl quizSubmissionService;
  private final UserService userService;
  private final CourseService courseService;
  private final EnrollmentService enrollmentService;
  private final ProgressService progressService;

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

  // Course operations

  public void createCourse(Course course) {
    courseService.createCourse(course);
  }

  public void uploadMedia(String courseId, MultipartFile file) {
    courseService.uploadMedia(courseId, file);
  }

  public List<String> getMediaForCourse(String courseId) {
    return courseService.getMediaForCourse(courseId);
  }

  public void addLessonToCourse(String courseId, Lesson lesson) {
    courseService.addLessonToCourse(courseId, lesson);
  }

  public List<Lesson> getLessonsForCourse(String courseId) {
    return courseService.getLessonsForCourse(courseId);
  }

  public Course findCourseById(String courseId) {
    return courseService.findCourseById(courseId);
  }

  public List<Course> getAllCourses() {
    return courseService.getAllCourses();
  }

  // Enrollment operations

  public void enrollStudent(String studentId, String courseId) {
    enrollmentService.enrollStudent(studentId, courseId);
  }

  public List<Enrollment> getEnrollmentsByCourse(String courseId) {
    return enrollmentService.getEnrollmentsByCourse(courseId);
  }

  public List<Enrollment> getEnrollmentsByStudent(String studentId) {
    return enrollmentService.getEnrollmentsByStudent(studentId);
  }

  public Map<String, List<User>> getCourseStudentMap() {
    return enrollmentService.getCourseStudentMap();
  }

  // Progress operations

  public List<StudentProgress> getAllStudentProgress() {
    return progressService.getAllStudentProgress();
  }

  public StudentProgress getStudentProgressByStudentId(String studentId) {
    return progressService.getStudentProgressByStudentId(studentId);
  }

  public StudentProgress getStudentProgressByStudentIdAndCourseId(
    String studentId,
    String courseId
  ) {
    return progressService.getStudentProgressByStudentIdAndCourseId(
      studentId,
      courseId
    );
  }

  public CourseProgress getCourseProgress(String courseId) {
    return progressService.getCourseProgress(courseId);
  }
}
