package com.lms.business.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizRequest {
  private String courseId;
  private String title;
  private int questionsNumber;
  private int duration;
  private String status;
}
