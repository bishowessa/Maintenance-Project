package com.lms.service.impl;

import com.lms.business.models.StudentAnswer;
import com.lms.persistence.entities.Quiz;
import com.lms.persistence.entities.QuizSubmission;
import com.lms.persistence.entities.questions.Question;
import com.lms.persistence.repositories.RepositoryFacade;
import com.lms.service.QuizSubmissionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
class QuizSubmissionServiceImpl implements QuizSubmissionService {

  private final RepositoryFacade repository;

  public QuizSubmissionServiceImpl(RepositoryFacade repository) {
    this.repository = repository;
  }

  @Override
  public QuizSubmission submitQuiz(
    String quizId,
    String studentId,
    Map<String, String> studentAnswers
  ) {
    Quiz quiz = repository.findQuizById(quizId).orElse(null);
    if (quiz != null) {
      List<Question> questions = quiz.getSelectedQuestions();
      QuizSubmission submission = new QuizSubmission();
      submission.setId(UUID.randomUUID().toString());
      submission.setQuizId(quizId);
      submission.setStudentId(studentId);
      List<StudentAnswer> answers = new ArrayList<>();

      for (Map.Entry<String, String> entry : studentAnswers.entrySet()) {
        Question question = questions
          .stream()
          .filter(q -> q.getId().equals(entry.getKey()))
          .findFirst()
          .orElse(null);

        if (question != null) {
          answers.add(
            new StudentAnswer(
              entry.getKey(),
              question.getQuestionText(),
              question.getGrade(),
              question.getCorrectAnswer(),
              entry.getValue()
            )
          );
        }
      }

      submission.setStudentAnswers(answers);
      repository.saveQuizSubmission(submission);
      return submission;
    } else {
      return null;
    }
  }

  @Override
  public List<QuizSubmission> getSubmissionsByStudent(String studentId) {
    return repository.findQuizSubmissionsByStudentId(studentId);
  }

  @Override
  public List<QuizSubmission> getAllSubmissions() {
    return repository.findAllQuizSubmissions();
  }

  @Override
  public List<QuizSubmission> getSubmissionsByQuiz(String quizId) {
    return repository.findQuizSubmissionsByQuizId(quizId);
  }

  @Override
  public List<QuizSubmission> getSubmissionsByStudentAndCourse(
    String studentId,
    String courseId
  ) {
    return repository.findQuizSubmissionsByStudentAndCourse(
      studentId,
      courseId
    );
  }
}
