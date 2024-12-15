package com.lms.presentation;

import com.lms.business.models.CourseProgress;
import com.lms.business.models.StudentProgress;
import com.lms.persistence.entities.AssignmentSubmissionEntity;
import com.lms.persistence.entities.QuizSubmission;
import com.lms.service.impl.ServiceFacade;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/progress")
public class ProgressController {

  private final ServiceFacade service;

  public ProgressController(ServiceFacade service) {
    this.service = service;
  }

  // Get all student progress
  @GetMapping("/students")
  public ResponseEntity<List<StudentProgress>> getAllStudentProgress() {
    List<StudentProgress> StudentsProgresses = new ArrayList<>();

    List<String> studentIds = Arrays.asList("1", "2", "3");

    for (String studentId : studentIds) {
      Map<String, List<QuizSubmission>> coursesQuizesSubmissions = new HashMap<>();
      Map<String, List<AssignmentSubmissionEntity>> coursesAssignmentsSubmissions = new HashMap<>();

      List<String> regestedCourcesIds = Arrays.asList("1", "2", "3");
      for (String courseId : regestedCourcesIds) {
        List<QuizSubmission> quizSubmissionsForCourse = service.getQuizSubmissionsByStudentAndCourse(
          studentId,
          courseId
        );
        List<AssignmentSubmissionEntity> assignmentSubmissionsForCourse = service.getAssignmentSubmissionsByStudentAndCourse(
          studentId,
          courseId
        );
        coursesQuizesSubmissions.put(courseId, quizSubmissionsForCourse);
        coursesAssignmentsSubmissions.put(
          courseId,
          assignmentSubmissionsForCourse
        );
      }

      StudentProgress studentProgress = new StudentProgress(
        studentId,
        coursesQuizesSubmissions,
        coursesAssignmentsSubmissions
      );

      StudentsProgresses.add(studentProgress);
    }
    return ResponseEntity.ok(StudentsProgresses);
  }

  // Get student progress by studentId
  @GetMapping("/students/{studentId}")
  public ResponseEntity<StudentProgress> getStudentProgressByCourseId(
    @PathVariable String studentId
  ) {
    Map<String, List<QuizSubmission>> coursesQuizesSubmissions = new HashMap<>();
    Map<String, List<AssignmentSubmissionEntity>> coursesAssignmentsSubmissions = new HashMap<>();

    List<String> regestedCourcesIds = Arrays.asList("1", "2", "3");
    for (String courseId : regestedCourcesIds) {
      List<QuizSubmission> quizSubmissionsForCourse = service.getQuizSubmissionsByStudentAndCourse(
        studentId,
        courseId
      );
      List<AssignmentSubmissionEntity> assignmentSubmissionsForCourse = service.getAssignmentSubmissionsByStudentAndCourse(
        studentId,
        courseId
      );

      coursesQuizesSubmissions.put(courseId, quizSubmissionsForCourse);
      coursesAssignmentsSubmissions.put(
        courseId,
        assignmentSubmissionsForCourse
      );
    }

    StudentProgress studentProgress = new StudentProgress(
      studentId,
      coursesQuizesSubmissions,
      coursesAssignmentsSubmissions
    );

    return ResponseEntity.ok(studentProgress);
  }

  // Get student progress by courseId
  @GetMapping("/students/{studentId}/{courseId}")
  public ResponseEntity<StudentProgress> getStudentProgressByCourseId(
    @PathVariable String studentId,
    @PathVariable String courseId
  ) {
    List<QuizSubmission> quizSubmissionsForCourse = service.getQuizSubmissionsByStudentAndCourse(
      studentId,
      courseId
    );
    List<AssignmentSubmissionEntity> assignmentSubmissionsForCourse = service.getAssignmentSubmissionsByStudentAndCourse(
      studentId,
      courseId
    );

    Map<String, List<QuizSubmission>> courseQuizesSubmissions = new HashMap<>();
    Map<String, List<AssignmentSubmissionEntity>> courseAssignmentsSubmissions = new HashMap<>();
    courseQuizesSubmissions.put(courseId, quizSubmissionsForCourse);
    courseAssignmentsSubmissions.put(courseId, assignmentSubmissionsForCourse);

    StudentProgress studentProgress = new StudentProgress(
      studentId,
      courseQuizesSubmissions,
      courseAssignmentsSubmissions
    );

    return ResponseEntity.ok(studentProgress);
  }

  // Get course progress
  @GetMapping("/courses/{courseId}")
  public ResponseEntity<CourseProgress> getCourseProgress(
    @PathVariable String courseId
  ) {
    //get all students Ids
    List<String> studentIds = Arrays.asList("123", "456", "789");

    Map<String, List<QuizSubmission>> StudentsQuizesSubmissions = new HashMap<>();
    Map<String, List<AssignmentSubmissionEntity>> StudentsAssignmentSubmissions = new HashMap<>();

    for (String studentId : studentIds) {
      //get the submissions and the attended lessons by the for the studentID and courseId
      List<QuizSubmission> quizSubmissionsForCourse = service.getQuizSubmissionsByStudentAndCourse(
        studentId,
        courseId
      );
      List<AssignmentSubmissionEntity> assignmentSubmissionsForCourse = service.getAssignmentSubmissionsByStudentAndCourse(
        studentId,
        courseId
      );

      StudentsQuizesSubmissions.put(studentId, quizSubmissionsForCourse);
      StudentsAssignmentSubmissions.put(
        studentId,
        assignmentSubmissionsForCourse
      );
    }
    CourseProgress courseProgress = new CourseProgress(
      courseId,
      StudentsQuizesSubmissions,
      StudentsAssignmentSubmissions
    );

    return ResponseEntity.ok(courseProgress);
  }
}
