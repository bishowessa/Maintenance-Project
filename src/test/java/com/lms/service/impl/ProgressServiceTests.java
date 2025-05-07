package com.lms.service.impl;

import com.lms.business.models.CourseProgress;
import com.lms.business.models.StudentProgress;
import com.lms.persistence.Course;
import com.lms.persistence.Lesson;
import com.lms.persistence.repositories.RepositoryFacade;
import com.lms.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProgressServiceTests {

    @Mock
    private RepositoryFacade repository;

    @Mock
    private CourseService courseService;

    private ProgressService progressService;

    private final String studentId = "S01";
    private final String courseId = "C01";

    @BeforeEach
    void setup() {
        repository = mock(RepositoryFacade.class);
        courseService = mock(CourseService.class);
        progressService = new ProgressService(repository, courseService);
    }

    @Test
    void testGetAllStudentProgress_Succeeds() {
        List<String> studentIds = List.of(studentId);
        List<Course> courses = List.of(mock(Course.class));
        StudentProgress expectedProgress = new StudentProgress(studentId, Map.of(), Map.of(), Map.of());

        when(repository.getAllStudentIds()).thenReturn(studentIds);
        when(repository.getAllRegisteredCourses(studentId)).thenReturn(courses);

        List<StudentProgress> result = progressService.getAllStudentProgress();

        assertEquals(1, result.size());
        assertEquals(expectedProgress.getStudentId(), result.get(0).getStudentId());
        verify(repository).getAllStudentIds();
        verify(repository).getAllRegisteredCourses(studentId);
    }

    @Test
    void testGetStudentProgressByStudentId_Succeeds() {
        Course course = mock(Course.class);
        List<Course> courses = List.of(course);

        when(repository.getAllRegisteredCourses(studentId)).thenReturn(courses);

        StudentProgress result = progressService.getStudentProgressByStudentId(studentId);

        assertNotNull(result);
        verify(repository).getAllRegisteredCourses(studentId);
    }

    @Test
    void testGetStudentProgressByStudentIdAndCourseId_Succeeds() {
        Course course = mock(Course.class);

        when(courseService.findCourseById(courseId)).thenReturn(course);

        StudentProgress result = progressService.getStudentProgressByStudentIdAndCourseId(studentId, courseId);

        assertNotNull(result);
        verify(courseService).findCourseById(courseId);
    }

    @Test
    void testGetCourseProgress_Succeeds() {
        Course course = mock(Course.class);
        List<Lesson> lessons = List.of(mock(Lesson.class));

        when(courseService.findCourseById(courseId)).thenReturn(course);
        when(course.getLessons()).thenReturn(lessons);
        when(repository.findAllQuizzes()).thenReturn(List.of());
        when(repository.findAllAssignments()).thenReturn(List.of());

        CourseProgress result = progressService.getCourseProgress(courseId);

        assertNotNull(result);
        verify(courseService).findCourseById(courseId);
    }
}
