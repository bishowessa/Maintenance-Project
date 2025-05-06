package com.lms.service.impl;

import com.lms.business.models.StudentAnswer;
import com.lms.events.NotificationEvent;
import com.lms.persistence.entities.Quiz;
import com.lms.persistence.entities.QuizSubmission;
import com.lms.persistence.entities.questions.Question;
import com.lms.persistence.repositories.RepositoryFacade;
import com.lms.service.CourseService;
import com.lms.service.QuizSubmissionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
class QuizSubmissionServiceImpl implements QuizSubmissionService {

  private final RepositoryFacade repository;
  private final CourseService courseService;

  @Override
  public QuizSubmission submitQuiz(String quizId, String studentId, Map<String, String> studentAnswers) {
    if (!repository.studentExistsById(studentId)) {
      throw new StudentNotFoundException("Student with ID " + studentId + " does not exist.");
    }

    Quiz quiz = repository.findQuizById(quizId).orElseThrow(() ->
            new QuizNotFoundException("Quiz with ID " + quizId + " does not exist.")
    );

    if (repository.findQuizSubmissionByStudentIdAndQuizId(studentId, quizId)) {
      throw new QuizAlreadySubmittedException("Student has already submitted the quiz.");
    }

    List<Question> questions = quiz.getSelectedQuestions();
    QuizSubmission submission = new QuizSubmission();
    submission.setId(UUID.randomUUID().toString().replace("-", "").substring(0, 6));
    submission.setQuizId(quizId);
    submission.setStudentId(studentId);
    submission.setCourseId(quiz.getCourseId());

    List<StudentAnswer> answers = new ArrayList<>();
    for (Map.Entry<String, String> entry : studentAnswers.entrySet()) {
      Question question = questions.stream()
              .filter(q -> q.getId().equals(entry.getKey()))
              .findFirst()
              .orElse(null);

      if (question != null) {
        answers.add(new StudentAnswer(
                entry.getKey(),
                question.getQuestionText(),
                question.getGrade(),
                question.getCorrectAnswer(),
                entry.getValue()
        ));
      }
    }

    submission.setStudentAnswers(answers);
    repository.saveQuizSubmission(submission);
    return submission;
  }

  @Override
  public List<QuizSubmission> getSubmissionsByStudent(String studentId) {
    if (!repository.studentExistsById(studentId)) {
      throw new StudentNotFoundException("Student with ID " + studentId + " does not exist.");
    }
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
  public List<QuizSubmission> getSubmissionsByStudentAndCourse(String studentId, String courseId) {
    if (!repository.studentExistsById(studentId)) {
      throw new StudentNotFoundException("Student with ID " + studentId + " does not exist.");
    }
    if (courseService.findCourseById(courseId) == null) {
      throw new CourseNotFoundException("Course with ID " + courseId + " does not exist.");
    }
    return repository.findQuizSubmissionsByStudentAndCourse(studentId, courseId);
  }
}
