package com.lms.service;

import com.lms.persistence.Course;
import com.lms.persistence.Lesson;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    private final List<Course> courseList = new ArrayList<>();  // In-memory storage for courses
    private final String mediaDirectory = "media/";  // Local directory for storing media

    public CourseServiceImpl() {
        File directory = new File(mediaDirectory);
        if (!directory.exists()) {
            directory.mkdirs();  // Create directory if it doesn't exist
        }
    }


    private long currentId = 1; // Counter for unique IDs

    @Override
    public void createCourse(Course course) {
        course.setId(currentId++); // Assign and increment the ID
        courseList.add(course);
    }
    @Override
    public void uploadMedia(Long courseId, MultipartFile file) {
        Course course = findCourseById(courseId);
        if (course != null) {
            try {
                String filePath = mediaDirectory + file.getOriginalFilename();
                File destinationFile = new File(filePath);
                file.transferTo(destinationFile);  // Save file to the local directory
                course.addMedia(filePath);  // Add file path to the course's media list
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload media file", e);
            }
        } else {
            throw new RuntimeException("Course not found with ID: " + courseId);
        }
    }

    @Override
    public List<String> getMediaForCourse(Long courseId) {
        Course course = findCourseById(courseId);
        if (course != null) {
            return course.getMediaPaths();
        } else {
            throw new RuntimeException("Course not found with ID: " + courseId);
        }
    }

    @Override
    public void addLessonToCourse(Long courseId, Lesson lesson) {
        Course course = findCourseById(courseId);
        if (course != null) {
            course.addLesson(lesson);
        } else {
            throw new RuntimeException("Course not found with ID: " + courseId);
        }
    }

    @Override
    public List<Lesson> getLessonsForCourse(Long courseId) {
        Course course = findCourseById(courseId);
        if (course != null) {
            return course.getLessons();
        } else {
            throw new RuntimeException("Course not found with ID: " + courseId);
        }
    }

    public Course findCourseById(Long courseId) {
        return courseList.stream()
                .filter(course -> course.getId().equals(courseId))
                .findFirst()
                .orElse(null);
    }
}
