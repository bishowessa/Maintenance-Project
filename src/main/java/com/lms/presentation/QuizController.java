package com.lms.presentation;

import com.lms.business.models.QuizRequest;
import com.lms.persistence.User;
import com.lms.persistence.entities.Quiz;
import com.lms.persistence.entities.QuizSubmission;
import com.lms.service.ServiceFacade;

import java.util.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quizzes")
public class QuizController {

  private final ServiceFacade service;

  public QuizController(ServiceFacade service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<Object> createQuiz(
    @RequestBody QuizRequest quizRequest
  ) {
    Optional<User> currentUser = service.getCurrentUser();
    if (currentUser.isEmpty()) {
      return ResponseEntity.status(404).build();
    }
    if (!"Instructor".equals(currentUser.get().getRole())) {
      return ResponseEntity
        .status(403)
        .body("Access Denied: You are unauthorized");
    }

    try {
      Quiz quiz = service.createQuiz(
        quizRequest.getCourseId(),
        quizRequest.getTitle(),
        quizRequest.getQuestionsNumber(),
        quizRequest.getDuration(),
        quizRequest.getStatus()
      );
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
    if (!"Instructor".equals(currentUser.get().getRole())) {
      return ResponseEntity
        .status(403)
        .body("Access Denied: You are unauthorized");
    }

    service.markQuizAsDeleted(quizId);
    return ResponseEntity.ok().body("Quiz " + quizId + " mark as deleted");
  }

  @PostMapping("/{quizId}/assign")
  public ResponseEntity<Object> assignQuiz(@PathVariable String quizId) {
    Optional<User> currentUser = service.getCurrentUser();

    if (currentUser.isEmpty()) {
      return ResponseEntity.status(404).build();
    }
    if (!"Instructor".equals(currentUser.get().getRole())) {
      return ResponseEntity
        .status(403)
        .body("Access Denied: You are unauthorized");
    }

    service.markQuizAsOpened(quizId);
    return ResponseEntity.ok().body("Quiz " + quizId + " mark as opened");
  }

  @PostMapping("/{quizId}/submit")
  public ResponseEntity<Object> submitQuiz(
    @PathVariable String quizId,
    @RequestParam String studentId,
    @RequestBody Map<String, Object> studentAnswers
  ) {
    Optional<User> currentUser = service.getCurrentUser();

    if (currentUser.isEmpty()) {
      return ResponseEntity.status(404).build();
    }
    if (!"Student".equals(currentUser.get().getRole())) {
      return ResponseEntity
        .status(403)
        .body("Access Denied: You are unauthorized");
    }

    Map<String, String> answers = new HashMap<>();
    for (Map.Entry<String, Object> entry : studentAnswers.entrySet()) {
      answers.put(entry.getKey(), entry.getValue().toString());
    }
    try {
      QuizSubmission submission = service.submitQuiz(
        quizId,
        studentId,
        answers
      );
      if (submission != null) {
        return ResponseEntity.ok(submission);
      } else {
        return ResponseEntity.status(404).body("Submission not found.");
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

    if (!"Instructor".equals(currentUser.get().getRole())) {
      return ResponseEntity
        .status(403)
        .body("Access Denied: You are unauthorized");
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

    if (!"Instructor".equals(currentUser.get().getRole())) {
      return ResponseEntity
        .status(403)
        .body("Access Denied: You are unauthorized");
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
      return ResponseEntity.status(404).body("Quiz not found.");
    }
  }
}
