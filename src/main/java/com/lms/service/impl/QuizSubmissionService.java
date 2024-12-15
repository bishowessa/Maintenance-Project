package com.lms.service.impl;

import com.lms.persistence.entities.Quiz;
import com.lms.persistence.entities.QuizSubmission;
import com.lms.persistence.repositories.QuizRepository;
import com.lms.persistence.repositories.QuizSubmissionRepository;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class QuizSubmissionService {

  private final QuizSubmissionRepository submissionRepository;
  private final QuizRepository quizRepository;

  public QuizSubmissionService(
    QuizSubmissionRepository submissionRepository,
    QuizRepository quizRepository
  ) {
    this.submissionRepository = submissionRepository;
    this.quizRepository = quizRepository;
  }

  public QuizSubmission submitQuiz(
    String quizId,
    String studentId,
    Map<String, String> studentAnswers
  ) {
    Quiz quiz = quizRepository.findById(quizId).orElse(null);
    if (quiz != null) {
      QuizSubmission submission = new QuizSubmission();
      submission.setId(UUID.randomUUID().toString());
      submission.setQuizId(quizId);
      submission.setStudentId(studentId);
      submission.setStudentAnswers(studentAnswers);
      double score = calculateScore(studentAnswers, quiz);
      submission.setScore(score);
      submissionRepository.save(submission);
      return submission;
    } else {
      return null;
    }
  }

  public List<QuizSubmission> getSubmissionsByStudent(String studentId) {
    return submissionRepository.findByStudentId(studentId);
  }

  private double calculateScore(Map<String, String> studentAnswers, Quiz quiz) {
    // Assuming quiz has a Map<String, String> correctAnswers (questionId -> correctAnswer)
    Map<String, String> correctAnswers = quiz.getCorrectAnswers();

    return studentAnswers
      .entrySet()
      .stream()
      .filter(entry -> {
        String questionId = entry.getKey();
        Object studentAnswer = entry.getValue();
        Object correctAnswer = correctAnswers.get(questionId);

        // Compare the student's answer with the correct answer
        return correctAnswer != null && correctAnswer.equals(studentAnswer);
      })
      .count(); // 1 point for each correct answer
  }
}
