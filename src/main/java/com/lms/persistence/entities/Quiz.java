package com.lms.persistence.entities;

import java.util.List;

import com.lms.persistence.entities.questions.Question;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class Quiz {

  private String id;
  private String CourseId;
  private String name;
  private int numberOfQuestions;
  private int duration;
  private String status;
  private List<Question> selectedQuestions;
}
