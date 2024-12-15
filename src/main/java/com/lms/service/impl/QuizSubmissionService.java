package com.lms.service.impl;

import com.lms.persistence.entities.Quiz;
import com.lms.persistence.entities.QuizSubmission;
import com.lms.persistence.entities.StudentAnswer;
import com.lms.persistence.entities.questions.Question;
import com.lms.persistence.repositories.QuizRepository;
import com.lms.persistence.repositories.QuizSubmissionRepository;
import java.util.ArrayList;
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
      submissionRepository.save(submission);
      return submission;
    } else {
      return null;
    }
  }

  public List<QuizSubmission> getSubmissionsByStudent(String studentId) {
    return submissionRepository.findByStudentId(studentId);
  }

  public List<QuizSubmission> getAllSubmissions() {
    return submissionRepository.findAll();
  }

  public List<QuizSubmission> getSubmissionsByQuiz(String quizId) {
    return submissionRepository.findByQuizId(quizId);
  }

}
