package com.lms.presentation;

import com.lms.business.models.QuizRequest;
import com.lms.events.CourseNotificationEvent;
import com.lms.events.NotificationEvent;
import com.lms.persistence.User;
import com.lms.persistence.entities.Quiz;
import com.lms.persistence.entities.QuizSubmission;
import com.lms.service.impl.ServiceFacade;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/quizzes")
@AllArgsConstructor
public class QuizController {

  private final ServiceFacade service;
  private ApplicationEventPublisher eventPublisher;

  // Constants for repeated literals
  private static final String EMAIL = "EMAIL";
  private static final String INSTRUCTOR_ROLE = "Instructor";
  private static final String ACCESS_DENIED = "Access Denied: You are unauthorized";
  private static final String QUIZ_NOT_FOUND = "Quiz not found";
  private static final String SUBMISSION_NOT_FOUND = "Submission not found";
  private static final String QUIZ_SUBMITTED_SUCCESSFULLY = "You submitted quiz %s successfully, Your score is: %s of %s";
  private static final String NEW_SUBMISSION = "New submission for quiz %s from %s";

  @PostMapping
  public ResponseEntity<Object> createQuiz(
          @RequestBody QuizRequest quizRequest
  ) {
    Optional<User> currentUser = service.getCurrentUser();
    if (currentUser.isEmpty()) {
      return ResponseEntity.status(404).build();
    }
    if (!INSTRUCTOR_ROLE.equals(currentUser.get().getRole())) {
      return ResponseEntity
              .status(403)
              .body(ACCESS_DENIED);
    }

    try {
      Quiz quiz = service.createQuiz(
              quizRequest.getCourseId(),
              quizRequest.getTitle(),
              currentUser.get().getId(),
              quizRequest.getQuestionsNumber(),
              quizRequest.getDuration(),
              quizRequest.getStatus()
      );

      String studentMessage = "New quiz " + quiz.getId() + " \"" + quiz.getName() + "\" in course " + quiz.getCourseId() +
              " number of questions is " + quiz.getNumberOfQuestions() + " quiz duration is " + quiz.getDuration();
      eventPublisher.publishEvent(new CourseNotificationEvent(this, quiz.getCourseId(), studentMessage));

      String instructorMessage = "You Published new quiz successfully";
      eventPublisher.publishEvent(new NotificationEvent(this, currentUser.get().getId(), instructorMessage, EMAIL));

      return ResponseEntity.ok(quiz);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping
  public ResponseEntity<List<Quiz>> getAllQuizzes() {
    Optional<User> currentUser = service.getCurrentUser();

    if (currentUser.isEmpty()) {
      return ResponseEntity.status(404).build();
    }

    return ResponseEntity.ok(service.getAllQuizzes());
  }

  @DeleteMapping("/{quizId}")
  public ResponseEntity<Object> deleteQuiz(@PathVariable String quizId) {
    Optional<User> currentUser = service.getCurrentUser();

    if (currentUser.isEmpty()) {
      return ResponseEntity.status(404).build();
    }
    if (!INSTRUCTOR_ROLE.equals(currentUser.get().getRole())) {
      return ResponseEntity
              .status(403)
              .body(ACCESS_DENIED);
    }

    String instructorMessage = "You Deleted quiz " + quizId + " successfully";
    eventPublisher.publishEvent(new NotificationEvent(this, currentUser.get().getId(), instructorMessage, EMAIL));

    service.markQuizAsDeleted(quizId);
    return ResponseEntity.ok().body("Quiz " + quizId + " marked as deleted");
  }

  @PostMapping("/{quizId}/assign")
  public ResponseEntity<Object> assignQuiz(@PathVariable String quizId) {
    Optional<User> currentUser = service.getCurrentUser();

    if (currentUser.isEmpty()) {
      return ResponseEntity.status(404).build();
    }
    if (!INSTRUCTOR_ROLE.equals(currentUser.get().getRole())) {
      return ResponseEntity
              .status(403)
              .body(ACCESS_DENIED);
    }

    service.markQuizAsOpened(quizId);

    String instructorMessage = "You Marked quiz " + quizId + " as opened successfully";
    eventPublisher.publishEvent(new NotificationEvent(this, currentUser.get().getId(), instructorMessage, EMAIL));

    return ResponseEntity.ok().body("Quiz " + quizId + " marked as opened");
  }

  @PostMapping("/{quizId}/submit")
  public ResponseEntity<Object> submitQuiz(
          @PathVariable String quizId,
          @RequestBody Map<String, Object> studentAnswers
  ) {
    Optional<User> currentUser = service.getCurrentUser();

    if (currentUser.isEmpty()) {
      return ResponseEntity.status(404).build();
    }
    if (!"Student".equals(currentUser.get().getRole())) {
      return ResponseEntity
              .status(403)
              .body(ACCESS_DENIED);
    }

    Quiz quiz = service.getQuizById(quizId);
    if (quiz == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(QUIZ_NOT_FOUND);
    }

    Map<String, String> answers = new HashMap<>();
    for (Map.Entry<String, Object> entry : studentAnswers.entrySet()) {
      answers.put(entry.getKey(), entry.getValue().toString());
    }
    try {
      QuizSubmission submission = service.submitQuiz(
              quizId,
              currentUser.get().getId(),
              answers
      );
      if (submission != null) {
        String studentMessage = String.format(QUIZ_SUBMITTED_SUCCESSFULLY, quizId, submission.getScore(), submission.getGrade());
        eventPublisher.publishEvent(new NotificationEvent(this, currentUser.get().getId(), studentMessage, EMAIL));

        String instructorMessage = String.format(NEW_SUBMISSION, quizId, currentUser.get().getId());
        eventPublisher.publishEvent(new NotificationEvent(this, quiz.getInstructorId(), instructorMessage, EMAIL));

        return ResponseEntity.ok(submission);
      } else {
        return ResponseEntity.status(404).body(SUBMISSION_NOT_FOUND);
      }
    } catch (Exception e) {
      return ResponseEntity
              .status(500)
              .body("Error submitting quiz: " + e.getMessage());
    }
  }

  @GetMapping("/submissions")
  public ResponseEntity<Object> getAllSubmissions() {
    Optional<User> currentUser = service.getCurrentUser();

    if (currentUser.isEmpty()) {
      return ResponseEntity.status(404).build();
    }

    if (!INSTRUCTOR_ROLE.equals(currentUser.get().getRole())) {
      return ResponseEntity
              .status(403)
              .body(ACCESS_DENIED);
    }

    return ResponseEntity.ok(service.getAllSubmissions());
  }

  @GetMapping("/submissions/{quizId}")
  public ResponseEntity<Object> getSubmissionsByQuiz(
          @PathVariable String quizId
  ) {
    Optional<User> currentUser = service.getCurrentUser();

    if (currentUser.isEmpty()) {
      return ResponseEntity.status(404).build();
    }

    if (!INSTRUCTOR_ROLE.equals(currentUser.get().getRole())) {
      return ResponseEntity
              .status(403)
              .body(ACCESS_DENIED);
    }

    return ResponseEntity.ok(service.getQuizSubmissionsByQuiz(quizId));
  }

  @GetMapping("/{quizId}")
  public ResponseEntity<Object> getQuizById(@PathVariable String quizId) {
    Optional<User> currentUser = service.getCurrentUser();

    if (currentUser.isEmpty()) {
      return ResponseEntity.status(404).build();
    }

    Quiz quiz = service.getQuizById(quizId);
    if (quiz != null) {
      return ResponseEntity.ok(quiz);
    } else {
      return ResponseEntity.status(404).body(QUIZ_NOT_FOUND);
    }
  }
}
