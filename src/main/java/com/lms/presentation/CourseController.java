package com.lms.presentation;

import com.lms.persistence.Course;
import com.lms.persistence.Lesson;
import com.lms.persistence.User;
import com.lms.service.AuthenticationService;
import com.lms.service.CourseService;
import com.lms.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    private final AuthenticationService authenticationService;

    public CourseController(CourseService courseService, AuthenticationService auth, UserService user) {
        this.courseService = courseService;
        this.authenticationService=auth;
        this.userService=user;
    }

    @PostMapping
    public ResponseEntity<String> createCourse(@RequestBody Course course) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails currentUserDetails = (UserDetails) authentication.getPrincipal();
        Optional<User> currentUser = userService.findByEmail(currentUserDetails.getUsername());

        if (currentUser.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        courseService.createCourse(course);
        return ResponseEntity.ok("Course created successfully!");
    }

    @PostMapping("/{courseId}/media")
    public ResponseEntity<String> uploadMedia(@PathVariable Long courseId, @RequestParam("file") MultipartFile file) {
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
            FileOutputStream fout = new FileOutputStream(filePath);
            fout.write(file.getBytes());
            fout.close();
            course.addMedia(filePath);
            return ResponseEntity.ok("File uploaded successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error in uploading file: " + e.getMessage());
        }
    }


    @GetMapping("/{courseId}/media")
    public ResponseEntity<List<String>> getMediaForCourse(@PathVariable Long courseId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails currentUserDetails = (UserDetails) authentication.getPrincipal();
        Optional<User> currentUser = userService.findByEmail(currentUserDetails.getUsername());

        if (currentUser.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.ok(courseService.getMediaForCourse(courseId));
    }

    @PostMapping("/{courseId}/lessons")
    public ResponseEntity<String> addLessonToCourse(@PathVariable Long courseId, @RequestBody Lesson lesson) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails currentUserDetails = (UserDetails) authentication.getPrincipal();
        Optional<User> currentUser = userService.findByEmail(currentUserDetails.getUsername());

        if (currentUser.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        courseService.addLessonToCourse(courseId, lesson);
        return ResponseEntity.ok("Lesson added successfully!");
    }

    @GetMapping("/{courseId}/lessons")
    public ResponseEntity<List<Lesson>> getLessonsForCourse(@PathVariable Long courseId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails currentUserDetails = (UserDetails) authentication.getPrincipal();
        Optional<User> currentUser = userService.findByEmail(currentUserDetails.getUsername());

        if (currentUser.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(courseService.getLessonsForCourse(courseId));
    }
}

