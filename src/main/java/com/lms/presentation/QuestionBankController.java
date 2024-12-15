package com.lms.presentation;

import com.lms.business.models.QuestionRequest;
import com.lms.persistence.entities.questions.Question;
import com.lms.persistence.entities.questions.QuestionFactory;
import com.lms.service.impl.QuestionBankService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questionBank")
public class QuestionBankController {

  private final QuestionBankService questionBankService;

  public QuestionBankController(QuestionBankService questionBankService) {
    this.questionBankService = questionBankService;
  }

  @PostMapping("/{courseId}/questions")
  public ResponseEntity<String> addQuestion(
    @PathVariable String courseId,
    @RequestBody QuestionRequest questionRequest
  ) {
    Question question = QuestionFactory.createQuestion(
      questionRequest.getType(),
      questionRequest
    );
    questionBankService.addQuestion(courseId, question);
    return ResponseEntity.ok("Question added successfully.");
  }

  @DeleteMapping("/{courseId}/questions/{questionId}")
  public ResponseEntity<String> deleteQuestion(
    @PathVariable String courseId,
    @PathVariable String questionId
  ) {
    questionBankService.deleteQuestion(courseId, questionId);
    return ResponseEntity.ok("Question deleted successfully.");
  }

  @GetMapping("/{courseId}/questions")
  public ResponseEntity<List<Question>> getQuestions(
    @PathVariable String courseId
  ) {
    return ResponseEntity.ok(questionBankService.getQuestions(courseId));
  }
}
