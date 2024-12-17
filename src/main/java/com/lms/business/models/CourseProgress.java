package com.lms.business.models;

import com.lms.persistence.Lesson;
import com.lms.persistence.User;
import com.lms.persistence.entities.AssignmentEntity;
import com.lms.persistence.entities.AssignmentSubmissionEntity;
import com.lms.persistence.entities.Quiz;
import com.lms.persistence.entities.QuizSubmission;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class CourseProgress {

  private String courseId;
  private Map<Quiz, List<QuizSubmission>> quizSubmissionByStudent = new HashMap<>(); // studentId -> list of quiz submissions
  private Map<AssignmentEntity, List<AssignmentSubmissionEntity>> assignmentSubmissionByStudent = new HashMap<>(); // studentId -> list of assignment submissions
  private Map<Lesson, List<User>> attendedLessonsByStudent = new HashMap<>(); // studentId -> list of attended lessons
}
