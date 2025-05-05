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

  // Constants for repeated literals
  private static final String NOT_AUTHENTICATED = "Not authenticated";
  private static final String INSTRUCTOR_ROLE = "Instructor";
  private static final String ACCESS_DENIED = "Access Denied: You are unauthorized";
  private static final String COURSE_NOT_FOUND = "Course not found";
  private static final String QUESTION_ADDED_SUCCESSFULLY = "Question added successfully.";
  private static final String QUESTIONS_ADDED_SUCCESSFULLY = "Questions added successfully.";
  private static final String QUESTION_DELETED_SUCCESSFULLY = "Question deleted successfully.";

  public QuestionBankController(ServiceFacade service) {
    this.service = service;
  }

  @PostMapping("/{courseId}/add1")
  public ResponseEntity<String> addQuestion(
          @PathVariable String courseId,
          @RequestBody QuestionRequest questionRequest
  ) {
    Optional<User> currentUser = service.getCurrentUser();
    if (currentUser.isEmpty()) {
      return ResponseEntity
              .status(HttpStatus.UNAUTHORIZED)
              .body(NOT_AUTHENTICATED);
    }

    if (!INSTRUCTOR_ROLE.equals(currentUser.get().getRole())) {
      return ResponseEntity
              .status(HttpStatus.FORBIDDEN)
              .body(ACCESS_DENIED);
    }

    if (service.findCourseById(courseId) == null) {
      return ResponseEntity.badRequest().body(COURSE_NOT_FOUND);
    }

    Question question = QuestionFactory.createQuestion(
            questionRequest.getType(),
            questionRequest
    );
    service.addQuestion(courseId, question);
    return ResponseEntity.ok(QUESTION_ADDED_SUCCESSFULLY);
  }

  @PostMapping("/{courseId}/add")
  public ResponseEntity<String> addQuestions(
          @PathVariable String courseId,
          @RequestBody List<QuestionRequest> questionRequests
  ) {
    Optional<User> currentUser = service.getCurrentUser();

    if (currentUser.isEmpty()) {
      return ResponseEntity
              .status(HttpStatus.UNAUTHORIZED)
              .body(NOT_AUTHENTICATED);
    }

    if (!INSTRUCTOR_ROLE.equals(currentUser.get().getRole())) {
      return ResponseEntity
              .status(HttpStatus.FORBIDDEN)
              .body(ACCESS_DENIED);
    }

    if (service.findCourseById(courseId) == null) {
      return ResponseEntity.badRequest().body(COURSE_NOT_FOUND);
    }

    for (QuestionRequest questionRequest : questionRequests) {
      Question question = QuestionFactory.createQuestion(
              questionRequest.getType(),
              questionRequest
      );
      service.addQuestion(courseId, question);
    }

    return ResponseEntity.ok(QUESTIONS_ADDED_SUCCESSFULLY);
  }

  @DeleteMapping("/{courseId}/questions/{questionId}")
  public ResponseEntity<String> deleteQuestion(
          @PathVariable String courseId,
          @PathVariable String questionId
  ) {
    Optional<User> currentUser = service.getCurrentUser();
    if (currentUser.isEmpty()) {
      return ResponseEntity
              .status(HttpStatus.UNAUTHORIZED)
              .body(NOT_AUTHENTICATED);
    }
    if (!INSTRUCTOR_ROLE.equals(currentUser.get().getRole())) {
      return ResponseEntity
              .status(HttpStatus.FORBIDDEN)
              .body(ACCESS_DENIED);
    }
    if (service.findCourseById(courseId) == null) {
      return ResponseEntity.badRequest().body(COURSE_NOT_FOUND);
    }
    service.deleteQuestion(courseId, questionId);
    return ResponseEntity.ok(QUESTION_DELETED_SUCCESSFULLY);
  }

  @GetMapping("/{courseId}/questions")
  public ResponseEntity<Object> getQuestions(@PathVariable String courseId) {
    Optional<User> currentUser = service.getCurrentUser();
    if (currentUser.isEmpty()) {
      return ResponseEntity
              .status(HttpStatus.UNAUTHORIZED)
              .body(Collections.emptyList());
    }
    if (!INSTRUCTOR_ROLE.equals(currentUser.get().getRole()) && !"Student".equals(currentUser.get().getRole())) {
      return ResponseEntity
              .status(HttpStatus.FORBIDDEN)
              .body(ACCESS_DENIED);
    }
    if (service.findCourseById(courseId) == null) {
      return ResponseEntity.badRequest().body(COURSE_NOT_FOUND);
    }
    return ResponseEntity.ok(service.getQuestions(courseId));
  }
}
