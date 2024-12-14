package com.lms.web;

import com.lms.business.models.QuizRequest;
import com.lms.persistence.entities.Quiz;
import com.lms.service.impl.QuizService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quizzes")
public class QuizController {

  private final QuizService quizService;

  public QuizController(QuizService quizService) {
    this.quizService = quizService;
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
}
