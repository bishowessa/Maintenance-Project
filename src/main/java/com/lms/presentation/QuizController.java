package com.lms.presentation;

import com.lms.business.models.QuizRequest;
import com.lms.persistence.entities.Quiz;
import com.lms.persistence.entities.QuizSubmission;
import com.lms.service.impl.QuizService;
import com.lms.service.impl.QuizSubmissionService;
import java.util.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quizzes")
public class QuizController {

  private final QuizService quizService;
  private final QuizSubmissionService quizSubmissionService;

  public QuizController(
    QuizService quizService,
    QuizSubmissionService quizSubmissionService
  ) {
    this.quizService = quizService;
    this.quizSubmissionService = quizSubmissionService;
  }

  @PostMapping
  public ResponseEntity<Quiz> createQuiz(@RequestBody QuizRequest quizRequest) {
    Quiz quiz = quizService.createQuiz(
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
    return ResponseEntity.ok(quizService.getAllQuizzes());
  }

  @DeleteMapping("/{quizId}")
  public void deleteQuiz(@PathVariable String quizId) {
    quizService.markQuizAsDeleted(quizId);
  }

  @PostMapping("/{quizId}/assign")
  public void assignQuiz(@PathVariable String quizId) {
    quizService.markQuizAsOpened(quizId);
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
      QuizSubmission submission = quizSubmissionService.submitQuiz(
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
    return ResponseEntity.ok(quizSubmissionService.getAllSubmissions());
  }

  @GetMapping("/submissions/{quizId}")
  public ResponseEntity<List<QuizSubmission>> getSubmissionsByQuiz(
    @PathVariable String quizId
  ) {
    return ResponseEntity.ok(
      quizSubmissionService.getSubmissionsByQuiz(quizId)
    );
  }

  @GetMapping("/{quizId}")
  public ResponseEntity<Object> getQuizById(@PathVariable String quizId) {
    Quiz quiz = quizService.getQuizById(quizId);
    if (quiz != null) {
      return ResponseEntity.ok(quiz);
    } else {
      return ResponseEntity.status(404).body("Quiz not found.");
    }
  }
}
