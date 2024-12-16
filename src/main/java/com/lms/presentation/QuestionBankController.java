package com.lms.presentation;

import com.lms.business.models.QuestionRequest;
import com.lms.persistence.User;
import com.lms.persistence.entities.questions.Question;
import com.lms.persistence.entities.questions.QuestionFactory;
import com.lms.service.impl.ServiceFacade;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/questionBank")
public class QuestionBankController {

  private final ServiceFacade service;

  public QuestionBankController(ServiceFacade service) {
    this.service = service;
  }

  @PostMapping("/{courseId}/questions")
  public ResponseEntity<String> addQuestion(
    @PathVariable String courseId,
    @RequestBody QuestionRequest questionRequest
  ) {
    Optional<User> currentUser = service.getCurrentUser();
    if (currentUser.isEmpty()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated");
    }
    if (!"Instructor".equals(currentUser.get().getRole())) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied: You are unauthorized");
    }
    Question question = QuestionFactory.createQuestion(
      questionRequest.getType(),
      questionRequest
    );
    service.addQuestion(courseId, question);
    return ResponseEntity.ok("Question added successfully.");
  }


  @DeleteMapping("/{courseId}/questions/{questionId}")
  public ResponseEntity<String> deleteQuestion(
    @PathVariable String courseId,
    @PathVariable String questionId
  ) {
    Optional<User> currentUser = service.getCurrentUser();
    if (currentUser.isEmpty()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated");
    }
    if (!"Instructor".equals(currentUser.get().getRole())) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied: You are unauthorized");
    }
    service.deleteQuestion(courseId, questionId);
    return ResponseEntity.ok("Question deleted successfully.");
  }

  @GetMapping("/{courseId}/questions")
  public ResponseEntity<Object> getQuestions(
    @PathVariable String courseId
  ) {
    Optional<User> currentUser = service.getCurrentUser();
    if (currentUser.isEmpty()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.emptyList());
    }
    if (!"Instructor".equals(currentUser.get().getRole()) && !"Student".equals(currentUser.get().getRole())) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied: You are unauthorized");
    }
    return ResponseEntity.ok(service.getQuestions(courseId));
  }
}
