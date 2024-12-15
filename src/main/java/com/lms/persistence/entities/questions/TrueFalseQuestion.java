package com.lms.persistence.entities.questions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrueFalseQuestion extends Question {

  private Boolean correctAnswer;

  public TrueFalseQuestion(
    String id,
    String questionText,
    boolean correctAnswer
  ) {
    super(id, "TrueFalse", questionText);
    this.correctAnswer = correctAnswer;
  }

  @Override
  public boolean validate() {
    return true;
  }

  @Override
  public String getCorrectAnswer() {
    return correctAnswer.toString();
  }
}
