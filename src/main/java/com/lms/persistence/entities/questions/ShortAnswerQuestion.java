package com.lms.persistence.entities.questions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShortAnswerQuestion extends Question {
  private String correctAnswer;

  public ShortAnswerQuestion(String id, String questionText, String correctAnswer) {
      super(id, "ShortAnswer", questionText);
      this.correctAnswer = correctAnswer;
  }

  @Override
  public boolean validate() {
      return correctAnswer != null && !correctAnswer.isEmpty();
  }

  @Override
  public String getCorrectAnswer() {
    return correctAnswer;
  }
}
