package com.lms.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class StudentAnswer {

  private String questionId;
  private String questionStatment;
  private int questionGrade;
  private String questionTrueAnswer;
  private String questionAnswer;
  private String status;

  public StudentAnswer(
    String questionId,
    String questionStatment,
    int questionGrade,
    String questionTrueAnswer,
    String questionAnswer
  ) {
    this.questionId = questionId;
    this.questionGrade = questionGrade;
    this.questionStatment = questionStatment;
    this.questionTrueAnswer = questionTrueAnswer;
    this.questionAnswer = questionAnswer;
    this.status =
      questionAnswer.equals(questionTrueAnswer) ? "correct" : "incorrect";
  }
}
