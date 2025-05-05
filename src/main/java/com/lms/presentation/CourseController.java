package com.lms.presentation;

import com.lms.events.CourseNotificationEvent;
import com.lms.events.NotificationEvent;
import com.lms.persistence.Course;
import com.lms.persistence.Lesson;
import com.lms.persistence.User;
import com.lms.service.CourseService;
import com.lms.service.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final UserService userService;
    private final CourseService courseService;
    private final ApplicationEventPublisher eventPublisher;

    // Constants to avoid duplication
    private static final String ROLE_INSTRUCTOR = "Instructor";
    private static final String ACCESS_DENIED = "Access Denied: you are unauthorized";

    public CourseController(CourseService courseService, UserService userService, ApplicationEventPublisher eventPublisher) {
        this.courseService = courseService;
        this.userService = userService;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping
    public ResponseEntity<String> createCourse(@RequestBody Course course) {
        Optional<User> currentUser = getCurrentUser();
        if (currentUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (!ROLE_INSTRUCTOR.equals(currentUser.get().getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ACCESS_DENIED);
        }

        course.setProfid(currentUser.get().getId());
        Course newCourse = courseService.createCourse(course);

        String message = "Course " + newCourse.getId() + " \"" + newCourse.getTitle() + "\" created successfully";
        eventPublisher.publishEvent(new NotificationEvent(this, currentUser.get().getId(), message, "EMAIL"));

        return ResponseEntity.ok("Course " + newCourse.getId() + " created successfully!");
    }

    @PostMapping("/{courseId}/media")
    public ResponseEntity<String> uploadMedia(@PathVariable String courseId, @RequestParam("file") MultipartFile file) {
        Optional<User> currentUser = getCurrentUser();
        if (currentUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (!ROLE_INSTRUCTOR.equals(currentUser.get().getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ACCESS_DENIED);
        }

        Course course = courseService.findCourseById(courseId);
        if (course == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found with ID: " + courseId);
        }

        String uploadDirectory = System.getProperty("user.dir") + "/Uploads";
        String filePath = uploadDirectory + File.separator + file.getOriginalFilename();

        try {
            File directory = new File(uploadDirectory);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            try (FileOutputStream fout = new FileOutputStream(filePath)) {
                fout.write(file.getBytes());
            }

            course.addMedia(filePath);

            String studentMessage = "course " + courseId + " \"" + course.getTitle() + "\" media updated successfully";
            eventPublisher.publishEvent(new CourseNotificationEvent(this, courseId, studentMessage));

            String instructorMessage = "You updated course " + courseId + " \"" + course.getTitle() + "\" media successfully";
            eventPublisher.publishEvent(new NotificationEvent(this, currentUser.get().getId(), instructorMessage, "EMAIL"));

            return ResponseEntity.ok("File uploaded successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error in uploading file: " + e.getMessage());
        }
    }

    @GetMapping("/{courseId}/media")
    public ResponseEntity<List<String>> getMediaForCourse(@PathVariable String courseId) {
        Optional<User> currentUser = getCurrentUser();
        if (currentUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(courseService.getMediaForCourse(courseId));
    }

    @PostMapping("/{courseId}/lessons")
    public ResponseEntity<String> addLessonToCourse(@PathVariable String courseId, @RequestBody Lesson lesson) {
        Optional<User> currentUser = getCurrentUser();
        if (currentUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (!ROLE_INSTRUCTOR.equals(currentUser.get().getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ACCESS_DENIED);
        }

        courseService.addLessonToCourse(courseId, lesson);
        return ResponseEntity.ok("Lesson added successfully!");
    }

    @GetMapping("/{courseId}/lessons")
    public ResponseEntity<List<Lesson>> getLessonsForCourse(@PathVariable String courseId) {
        Optional<User> currentUser = getCurrentUser();
        if (currentUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(courseService.getLessonsForCourse(courseId));
    }

    @GetMapping("/availableCourses")
    public ResponseEntity<List<Course>> getAllCourses() {
        Optional<User> currentUser = getCurrentUser();
        if (currentUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    // Utility method to fetch the authenticated user
    private Optional<User> getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = authentication.getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            return userService.findByEmail(userDetails.getUsername());
        }
        return Optional.empty();
    }
}
