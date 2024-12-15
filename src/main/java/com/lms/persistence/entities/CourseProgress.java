package com.lms.persistence.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class CourseProgress {

  private String courseId;
  private Map<String, List<QuizSubmission>> quizSubmission = new HashMap<>();
  private Map<String, List<AssignmentSubmissionEntity>> assignmentSubmission = new HashMap<>();
  // private List<String> attendedLessonsIds = new ArrayList<>();
}
