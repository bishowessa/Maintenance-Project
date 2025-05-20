package com.lms.listeners;

import com.lms.events.CourseNotificationEvent;
import com.lms.manager.NotificationManager;
import com.lms.persistence.Notification;
import com.lms.service.EnrollmentService;
import com.lms.service.UserService;
import com.lms.service.impl.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CourseNotificationEventListener {

    private static final Logger logger = LoggerFactory.getLogger(CourseNotificationEventListener.class);

    private final EmailService emailService;
    private final EnrollmentService enrollmentService;
    private final UserService userService;
    private final NotificationManager notificationManager;

    public CourseNotificationEventListener(
            EnrollmentService enrollmentService,
            EmailService emailService,
            UserService userService,
            NotificationManager notificationManager
    ) {
        this.enrollmentService = enrollmentService;
        this.emailService = emailService;
        this.userService = userService;
        this.notificationManager = notificationManager;
    }

    @EventListener
    @Async
    public void handleCourseNotificationEvent(CourseNotificationEvent event) {
        String courseId = event.getCourseId();
        String message = event.getMessage();

        Notification notification = new Notification();
        notification.setMessage(message);

        enrollmentService.getEnrollmentsByCourse(courseId).forEach(enrollment -> {
            notification.setUserId(enrollment.getsId());
            notificationManager.addNotification(notification);

            String email = userService.findById(enrollment.getsId()).getEmail();
            String subject = "Notification for Course: " + courseId;

            new Thread(() -> sendEmailSafe(email, subject, message, enrollment.getsId())).start();
        });

        logger.info("Notification sent to all students in course: {}", courseId);
    }

    private void sendEmailSafe(String email, String subject, String message, String userId) {
        try {
            emailService.sendEmail(email, subject, message);
        } catch (Exception e) {
            logger.error("Couldn't send the email to user {}: {}", userId, e.getMessage(), e);
        }
    }
}
