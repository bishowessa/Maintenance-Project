package com.lms.presentation;

import com.lms.business.models.QuizRequest;
import com.lms.persistence.entities.Quiz;
import com.lms.persistence.entities.QuizSubmission;
import com.lms.service.impl.ServiceFacade;

import java.util.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quizzes")
public class QuizController {

  private final ServiceFacade service;
  public QuizController(
    ServiceFacade service
  ) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<Quiz> createQuiz(@RequestBody QuizRequest quizRequest) {
    Quiz quiz = service.createQuiz(
      quizRequest.getCourseId(),
      quizRequest.getTitle(),
      quizRequest.getQuestionsNumber(),
      quizRequest.getDuration(),
      quizRequest.getStatus()
    );
    return ResponseEntity.ok(quiz);
  }

  @GetMapping
  public ResponseEntity<List<Quiz>> getAllQuizzes() {
    return ResponseEntity.ok(service.getAllQuizzes());
  }

  @DeleteMapping("/{quizId}")
  public void deleteQuiz(@PathVariable String quizId) {
    service.markQuizAsDeleted(quizId);
  }

  @PostMapping("/{quizId}/assign")
  public void assignQuiz(@PathVariable String quizId) {
    service.markQuizAsOpened(quizId);
  }

  @PostMapping("/{quizId}/submit")
  public ResponseEntity<Object> submitQuiz(
    @PathVariable String quizId,
    @RequestParam String studentId,
    @RequestBody Map<String, Object> studentAnswers
  ) {
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
  public ResponseEntity<List<QuizSubmission>> getAllSubmissions() {
    return ResponseEntity.ok(service.getAllSubmissions());
  }

  @GetMapping("/submissions/{quizId}")
  public ResponseEntity<List<QuizSubmission>> getSubmissionsByQuiz(
    @PathVariable String quizId
  ) {
    return ResponseEntity.ok(
      service.getQuizSubmissionsByQuiz(quizId)
    );
  }

  @GetMapping("/{quizId}")
  public ResponseEntity<Object> getQuizById(@PathVariable String quizId) {
    Quiz quiz = service.getQuizById(quizId);
    if (quiz != null) {
      return ResponseEntity.ok(quiz);
    } else {
      return ResponseEntity.status(404).body("Quiz not found.");
    }
  }
}
