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
public class RepositoryFacadeImpl implements RepositoryFacade {

  private final AssignmentRepository assignmentRepository;
  private final AssignmentSubmissionRepository assignmentSubmissionRepository;
  private final QuestionBankRepository questionBankRepository;
  private final QuizRepository quizRepository;
  private final QuizSubmissionRepository quizSubmissionRepository;

  public RepositoryFacadeImpl(
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
  @Override
  public boolean addAssignment(AssignmentEntity entity) {
    return assignmentRepository.add(entity);
  }

  @Override
  public List<AssignmentEntity> findAllAssignments() {
    return assignmentRepository.findAll();
  }

  @Override
  public AssignmentEntity findAssignmentById(int id) {
    return assignmentRepository.findById(id);
  }

  // Assignment Submission operations
  @Override
  public boolean addAssignmentSubmission(
    AssignmentSubmissionEntity submission
  ) {
    return assignmentSubmissionRepository.add(submission);
  }

  @Override
  public List<AssignmentSubmissionEntity> findAllAssignmentSubmissions() {
    return assignmentSubmissionRepository.findAll();
  }

  @Override
  public AssignmentSubmissionEntity findAssignmentSubmissionById(int id) {
    return assignmentSubmissionRepository.findById(id);
  }

  @Override
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
  @Override
  public void saveQuestionBank(QuestionBank questionBank) {
    questionBankRepository.save(questionBank);
  }

  @Override
  public QuestionBank findQuestionBankByCourseId(String courseId) {
    return questionBankRepository.findByCourseId(courseId);
  }

  // Quiz operations
  @Override
  public void saveQuiz(Quiz quiz) {
    quizRepository.save(quiz);
  }

  @Override
  public Optional<Quiz> findQuizById(String quizId) {
    return quizRepository.findById(quizId);
  }

  @Override
  public List<Quiz> findAllQuizzes() {
    return quizRepository.findAll();
  }

  // Quiz Submission operations
  @Override
  public void saveQuizSubmission(QuizSubmission submission) {
    quizSubmissionRepository.save(submission);
  }

  @Override
  public List<QuizSubmission> findAllQuizSubmissions() {
    return quizSubmissionRepository.findAll();
  }

  @Override
  public List<QuizSubmission> findQuizSubmissionsByStudentId(String studentId) {
    return quizSubmissionRepository.findByStudentId(studentId);
  }

  @Override
  public List<QuizSubmission> findQuizSubmissionsByQuizId(String quizId) {
    return quizSubmissionRepository.findByQuizId(quizId);
  }

  @Override
  public List<QuizSubmission> findQuizSubmissionsByStudentAndCourse(
    String studentId,
    String courseId
  ) {
    return quizSubmissionRepository.findByStudentAndCourse(studentId, courseId);
  }

  @Override
  public boolean isAssignmentExist(int assignmentId) {
    return assignmentRepository.isExist(assignmentId);
  }

  @Override
  public boolean updateAssignmentSubmission(AssignmentSubmissionEntity entity) {
    return assignmentSubmissionRepository.update(entity);
  }

  @Override
  public void updateQuiz(Quiz quiz) {
    quizRepository.update(quiz);
  }


}
