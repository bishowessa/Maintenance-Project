package com.lms.persistence.repositories;

import com.lms.persistence.entities.AssignmentEntity;
import com.lms.persistence.entities.AssignmentSubmissionEntity;
import com.lms.persistence.entities.QuestionBank;
import com.lms.persistence.entities.Quiz;
import com.lms.persistence.entities.QuizSubmission;
import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class RepositoryFacade {

  private final AssignmentRepository assignmentRepository;
  private final AssignmentSubmissionRepository assignmentSubmissionRepository;
  private final QuestionBankRepository questionBankRepository;
  private final QuizRepository quizRepository;
  private final QuizSubmissionRepository quizSubmissionRepository;

  public RepositoryFacade(
    AssignmentRepository assignmentRepository,
    AssignmentSubmissionRepository assignmentSubmissionRepository,
    QuestionBankRepository questionBankRepository,
    QuizRepository quizRepository,
    QuizSubmissionRepository quizSubmissionRepository
  ) {
    this.assignmentRepository = assignmentRepository;
    this.assignmentSubmissionRepository = assignmentSubmissionRepository;
    this.questionBankRepository = questionBankRepository;
    this.quizRepository = quizRepository;
    this.quizSubmissionRepository = quizSubmissionRepository;
  }

  // Assignment operations
  public boolean addAssignment(AssignmentEntity entity) {
    return assignmentRepository.add(entity);
  }

  public List<AssignmentEntity> findAllAssignments() {
    return assignmentRepository.findAll();
  }

  public AssignmentEntity findAssignmentById(int id) {
    return assignmentRepository.findById(id);
  }

  // Assignment Submission operations
  public boolean addAssignmentSubmission(
    AssignmentSubmissionEntity submission
  ) {
    return assignmentSubmissionRepository.add(submission);
  }

  public List<AssignmentSubmissionEntity> findAllAssignmentSubmissions() {
    return assignmentSubmissionRepository.findAll();
  }

  public AssignmentSubmissionEntity findAssignmentSubmissionById(int id) {
    return assignmentSubmissionRepository.findById(id);
  }

  public List<AssignmentSubmissionEntity> findAssignmentSubmissionsByStudentAndCourse(
    String studentId,
    String courseId
  ) {
    return assignmentSubmissionRepository.findByStudentAndCourse(
      studentId,
      courseId
    );
  }

  // Question Bank operations
  public void saveQuestionBank(QuestionBank questionBank) {
    questionBankRepository.save(questionBank);
  }

  public QuestionBank findQuestionBankByCourseId(String courseId) {
    return questionBankRepository.findByCourseId(courseId);
  }

  // Quiz operations
  public void saveQuiz(Quiz quiz) {
    quizRepository.save(quiz);
  }

  public Optional<Quiz> findQuizById(String quizId) {
    return quizRepository.findById(quizId);
  }

  public List<Quiz> findAllQuizzes() {
    return quizRepository.findAll();
  }

  // Quiz Submission operations
  public void saveQuizSubmission(QuizSubmission submission) {
    quizSubmissionRepository.save(submission);
  }

  public List<QuizSubmission> findAllQuizSubmissions() {
    return quizSubmissionRepository.findAll();
  }

  public List<QuizSubmission> findQuizSubmissionsByStudentId(String studentId) {
    return quizSubmissionRepository.findByStudentId(studentId);
  }

  public List<QuizSubmission> findQuizSubmissionsByQuizId(String quizId) {
    return quizSubmissionRepository.findByQuizId(quizId);
  }

  public List<QuizSubmission> findQuizSubmissionsByStudentAndCourse(
    String studentId,
    String courseId
  ) {
    return quizSubmissionRepository.findByStudentAndCourse(studentId, courseId);
  }

  public boolean isAssignmentExist(int assignmentId) {
    return assignmentRepository.isExist(assignmentId);
  }

  public boolean updateAssignmentSubmission(AssignmentSubmissionEntity entity) {
    return assignmentSubmissionRepository.update(entity);
  }

  public void updateQuiz(Quiz quiz) {
    quizRepository.update(quiz);
  }


}
