package com.lms.listeners;

import com.lms.events.CourseNotificationEvent;
import com.lms.service.EnrollmentService;
import com.lms.service.EnrollmentServiceImpl;
import com.lms.service.UserService;
import com.lms.service.impl.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CourseNotificationEventListener {


    private final EmailService emailService;     // Service to send emails
    private final EnrollmentService enrollmentService;     // Service to send emails
    private final UserService userService;

    public CourseNotificationEventListener(EnrollmentService enrollmentService, EmailService emailService, EnrollmentServiceImpl enrollmentServiceImpl, UserService userService) {
        this.enrollmentService = enrollmentService;
        this.emailService = emailService;
        this.userService = userService;
    }

    @EventListener
    public void handleCourseNotificationEvent(CourseNotificationEvent event) {
        String courseId = event.getCourseId();
        String message = event.getMessage();

        // Fetch students registered in the course
        enrollmentService.getEnrollmentsByCourse(courseId).forEach(enrollment -> {
            String email = userService.findById(enrollment.getsId()).getEmail() ;
            String subject = "Notification for Course: " + courseId;
            emailService.sendEmail(email, subject, message);
        });

        System.out.println("Notification sent to all students in course: " + courseId);
    }
}
