package com.lms.business.models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionRequest {

  private String type;
  private String questionText;
  private List<String> options;
  private String correctAnswer;
  private Boolean correctAnswerBoolean;
}
