package com.lms.service;

import com.lms.persistence.Course;
import com.lms.persistence.Lesson;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CourseService {
    void createCourse(Course course);
    void uploadMedia(Long courseId, MultipartFile file);
    List<String> getMediaForCourse(Long courseId);
    void addLessonToCourse(Long courseId, Lesson lesson);
    List<Lesson> getLessonsForCourse(Long courseId);
    Course findCourseById(Long courseId);

    public List<Course> getAllCourses();
}