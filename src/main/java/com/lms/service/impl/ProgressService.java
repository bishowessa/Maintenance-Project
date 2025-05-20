package com.lms.service.impl;

import com.lms.business.models.CourseProgress;
import com.lms.business.models.StudentProgress;
import com.lms.persistence.Course;
import com.lms.persistence.Lesson;
import com.lms.persistence.User;
import com.lms.persistence.entities.AssignmentEntity;
import com.lms.persistence.entities.AssignmentSubmissionEntity;
import com.lms.persistence.entities.Quiz;
import com.lms.persistence.entities.QuizSubmission;
import com.lms.persistence.repositories.RepositoryFacade;
import com.lms.service.CourseService;
import com.lms.service.SmsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class ProgressService {

  private final RepositoryFacade repository;
  private final CourseService courseService;

  public List<StudentProgress> getAllStudentProgress() {
    return repository.getAllStudentIds()
            .stream()
            .map(this::getStudentProgressByStudentId)
            .collect(Collectors.toList());
  }

  public StudentProgress getStudentProgressByStudentId(String studentId) {
    return getStudentProgress(studentId, repository.getAllRegisteredCourses(studentId));
  }

  public StudentProgress getStudentProgressByStudentIdAndCourseId(String studentId, String courseId) {
    Course course = courseService.findCourseById(courseId);
    return getStudentProgress(studentId, List.of(course));
  }

  private StudentProgress getStudentProgress(String studentId, List<Course> courses) {
    Map<String, List<QuizSubmission>> quizSubmissionsByCourse = new HashMap<>();
    Map<String, List<AssignmentSubmissionEntity>> assignmentSubmissionsByCourse = new HashMap<>();
    Map<String, List<Lesson>> attendedLessonsByCourse = new HashMap<>();

    for (Course course : courses) {
      quizSubmissionsByCourse.put(
              course.getId(),
              repository.findQuizSubmissionsByStudentAndCourse(studentId, course.getId())
      );
      assignmentSubmissionsByCourse.put(
              course.getId(),
              repository.findAssignmentSubmissionsByStudentAndCourse(studentId, course.getId())
      );
      attendedLessonsByCourse.put(
              course.getId(),
              getAttendedLessonsForCourse(studentId, course)
      );
    }

    return new StudentProgress(
            studentId,
            quizSubmissionsByCourse,
            assignmentSubmissionsByCourse,
            attendedLessonsByCourse
    );
  }

  private List<Lesson> getAttendedLessonsForCourse(String studentId, Course course) {
    return course.getLessons()
            .stream()
            .filter(lesson -> isLessonAttended(studentId, lesson))
            .collect(Collectors.toList());
  }

  private boolean isLessonAttended(String studentId, Lesson lesson) {
      SmsService.viewAttendance()
              .stream()
              .filter(pair -> pair.getKey().equals(lesson.getTitle()))
              .flatMap(pair -> pair.getValue().stream())
              .filter(Objects::nonNull)
              .anyMatch(user -> user.getId().equals(studentId));
      return false;
  }

  public CourseProgress getCourseProgress(String courseId) {
    Course course = courseService.findCourseById(courseId);

    Map<String, List<QuizSubmission>> quizSubmissionsByQuiz = repository.findAllQuizzes()
            .stream()
            .filter(quiz -> quiz.getCourseId().equals(courseId))
            .collect(Collectors.toMap(
                    Quiz::getId,
                    quiz -> repository.findQuizSubmissionsByQuizId(quiz.getId())
            ));

    Map<Integer, List<AssignmentSubmissionEntity>> assignmentSubmissionsByAssignment = repository.findAllAssignments()
            .stream()
            .filter(assignment -> assignment.getCourseId().equals(courseId))
            .collect(Collectors.toMap(
                    AssignmentEntity::getId,
                    assignment -> repository.findByAssignmentSubmissionsByAssignmentId(assignment.getId())
            ));

    Map<String, List<User>> lessonsAttendanceByLesson = course.getLessons()
            .stream()
            .collect(Collectors.toMap(
                    Lesson::getId,
                    lesson -> SmsService.viewAttendance()
                            .stream()
                            .filter(pair -> pair.getKey().equals(lesson.getTitle()))
                            .flatMap(pair -> pair.getValue().stream())
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList())
            ));

    return new CourseProgress(
            courseId,
            quizSubmissionsByQuiz,
            assignmentSubmissionsByAssignment,
            lessonsAttendanceByLesson
    );
  }
}
