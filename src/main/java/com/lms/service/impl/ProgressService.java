package com.lms.service.impl;

import com.lms.business.models.CourseProgress;
import com.lms.business.models.StudentProgress;
import com.lms.persistence.entities.AssignmentSubmissionEntity;
import com.lms.persistence.entities.QuizSubmission;
import com.lms.persistence.repositories.RepositoryFacade;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class ProgressService {

  private final RepositoryFacade repository;

  // Get all student progress
  public List<StudentProgress> getAllStudentProgress() {

    List<StudentProgress> studentsProgresses = new ArrayList<>();

    List<String> studentIds = Arrays.asList("1", "2", "3");

    for (String studentId : studentIds) {
      Map<String, List<QuizSubmission>> coursesQuizesSubmissions = new HashMap<>();
      Map<String, List<AssignmentSubmissionEntity>> coursesAssignmentsSubmissions = new HashMap<>();

      List<String> regestedCourcesIds = Arrays.asList("1", "2", "3");
      for (String courseId : regestedCourcesIds) {
        List<QuizSubmission> quizSubmissionsForCourse = repository.findQuizSubmissionsByStudentAndCourse(
          studentId,
          courseId
        );
        List<AssignmentSubmissionEntity> assignmentSubmissionsForCourse = repository.findAssignmentSubmissionsByStudentAndCourse(
          studentId,
          courseId
        );
        coursesQuizesSubmissions.put(courseId, quizSubmissionsForCourse);
        coursesAssignmentsSubmissions.put(
          courseId,
          assignmentSubmissionsForCourse
        );
      }

      studentsProgresses.add(
        new StudentProgress(
          studentId,
          coursesQuizesSubmissions,
          coursesAssignmentsSubmissions
        )
      );
    }
    return studentsProgresses;
  }

  // Get student progress by studentId
  public StudentProgress getStudentProgressByStudentId(String studentId) {
    Map<String, List<QuizSubmission>> coursesQuizesSubmissions = new HashMap<>();
    Map<String, List<AssignmentSubmissionEntity>> coursesAssignmentsSubmissions = new HashMap<>();

    List<String> regestedCourcesIds = Arrays.asList("1", "2", "3");
    for (String courseId : regestedCourcesIds) {
      List<QuizSubmission> quizSubmissionsForCourse = repository.findQuizSubmissionsByStudentAndCourse(
        studentId,
        courseId
      );
      List<AssignmentSubmissionEntity> assignmentSubmissionsForCourse = repository.findAssignmentSubmissionsByStudentAndCourse(
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

    return studentProgress;
  }

  // Get student progress by courseId
  public StudentProgress getStudentProgressByStudentIdAndCourseId(
    String studentId,
    String courseId
  ) {
    List<QuizSubmission> quizSubmissionsForCourse = repository.findQuizSubmissionsByStudentAndCourse(
      studentId,
      courseId
    );
    List<AssignmentSubmissionEntity> assignmentSubmissionsForCourse = repository.findAssignmentSubmissionsByStudentAndCourse(
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

    return studentProgress;
  }

  // Get course progress
  public CourseProgress getCourseProgress(String courseId) {
    //get all students Ids
    List<String> studentIds = Arrays.asList("123", "456", "789");

    Map<String, List<QuizSubmission>> StudentsQuizesSubmissions = new HashMap<>();
    Map<String, List<AssignmentSubmissionEntity>> StudentsAssignmentSubmissions = new HashMap<>();

    for (String studentId : studentIds) {
      //get the submissions and the attended lessons by the for the studentID and courseId
      List<QuizSubmission> quizSubmissionsForCourse = repository.findQuizSubmissionsByStudentAndCourse(
        studentId,
        courseId
      );
      List<AssignmentSubmissionEntity> assignmentSubmissionsForCourse = repository.findAssignmentSubmissionsByStudentAndCourse(
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

    return courseProgress;
  }
}
