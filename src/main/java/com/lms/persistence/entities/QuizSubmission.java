package com.lms.persistence.entities;

import com.lms.persistence.entities.questions.Question;
import java.util.List;
import java.util.Map;
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
public class QuizSubmission {

  private String id;
  private String studentId;
  private String quizId;
  private Double score;
  private Map<String, String> studentAnswers;
}
