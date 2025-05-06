package com.lms.service;

import com.lms.persistence.Course;
import com.lms.persistence.Lesson;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CourseServiceImpl implements CourseService {

    private static final String MEDIA_DIRECTORY = "media/";  // Static constant for media directory
    private final List<Course> courseList = new ArrayList<>();  // In-memory storage for courses

    public CourseServiceImpl() {
        File directory = new File(MEDIA_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();  // Create directory if it doesn't exist
        }
    }

    @Override
    public Course createCourse(Course course) {
        course.setId(UUID.randomUUID().toString().replace("-", "").substring(0, 6)); // Generate unique ID
        courseList.add(course);
        return course;
    }

    @Override
    public void uploadMedia(String courseId, MultipartFile file) throws CourseNotFoundException {
        Course course = findCourseById(courseId);
        if (course != null) {
            try {
                String filePath = MEDIA_DIRECTORY + file.getOriginalFilename();
                File destinationFile = new File(filePath);
                file.transferTo(destinationFile);  // Save file to the local directory
                course.addMedia(filePath);  // Add file path to the course's media list
            } catch (IOException e) {
                throw new IOException("Failed to upload media file", e);
            }
        } else {
            throw new CourseNotFoundException("Course not found with ID: " + courseId);
        }
    }

    @Override
    public List<String> getMediaForCourse(String courseId) throws CourseNotFoundException {
        Course course = findCourseById(courseId);
        if (course != null) {
            return course.getMediaPaths();
        } else {
            throw new CourseNotFoundException("Course not found with ID: " + courseId);
        }
    }

    @Override
    public void addLessonToCourse(String courseId, Lesson lesson) throws CourseNotFoundException {
        Course course = findCourseById(courseId);
        if (course != null) {
            course.addLesson(lesson);
        } else {
            throw new CourseNotFoundException("Course not found with ID: " + courseId);
        }
    }

    @Override
    public List<Lesson> getLessonsForCourse(String courseId) throws CourseNotFoundException {
        Course course = findCourseById(courseId);
        if (course != null) {
            return course.getLessons();
        } else {
            throw new CourseNotFoundException("Course not found with ID: " + courseId);
        }
    }

    @Override
    public Course findCourseById(String courseId) {
        return courseList.stream()
                .filter(course -> course.getId().equals(courseId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Course> getAllCourses() {
        return new ArrayList<>(courseList);
    }
}
