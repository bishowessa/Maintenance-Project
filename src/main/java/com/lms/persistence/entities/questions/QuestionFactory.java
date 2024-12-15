package com.lms.persistence.entities.questions;

import com.lms.business.models.QuestionRequest;

import java.util.UUID;

public class QuestionFactory {

  public static Question createQuestion(
    String type,
    QuestionRequest questionRequest
  ) {
    switch (type) {
      case "MCQ":
        return new MCQQuestion(
          UUID.randomUUID().toString(),
          questionRequest.getQuestionText(),
          questionRequest.getOptions(),
          questionRequest.getCorrectAnswer()
        );
      case "TrueFalse":
        return new TrueFalseQuestion(
          UUID.randomUUID().toString(),
          questionRequest.getQuestionText(),
          questionRequest.getCorrectAnswerBoolean()
        );
      case "ShortAnswer":
        return new ShortAnswerQuestion(
          UUID.randomUUID().toString(),
          questionRequest.getQuestionText(),
          questionRequest.getCorrectAnswer()
        );
      default:
        throw new IllegalArgumentException("Invalid question type.");
    }
  }
}
