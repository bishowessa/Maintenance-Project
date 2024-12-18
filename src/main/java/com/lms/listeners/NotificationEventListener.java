package com.lms.listeners;

import com.lms.events.NotificationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import com.lms.service.NotificationServiceImpl;


@Component
public class NotificationEventListener {

    private final NotificationServiceImpl notificationService;

    public NotificationEventListener(NotificationServiceImpl notificationService) {
        this.notificationService = notificationService;
    }

    @EventListener
    public void handleNotificationEvent(NotificationEvent event) {
        notificationService.saveNotification(event.getStudentId(), event.getMessage());

        System.out.println("Notification Event Received:");
        System.out.println("Student ID: " + event.getStudentId());
        System.out.println("Message: " + event.getMessage());
        System.out.println("Notification Type: " + event.getNotificationType());

        // Simulate notification sending
        switch (event.getNotificationType()) {
            case "EMAIL":
                sendEmailNotification(event);
                break;
            case "SMS":
                sendSMSNotification(event);
                break;
            case "IN_APP":
                sendInAppNotification(event);
                break;
        }
    }

    private void sendEmailNotification(NotificationEvent event) {
        System.out.println("Sending EMAIL to student " + event.getStudentId() + ": " + event.getMessage());
    }

    private void sendSMSNotification(NotificationEvent event) {
        System.out.println("Sending SMS to student " + event.getStudentId() + ": " + event.getMessage());
    }

    private void sendInAppNotification(NotificationEvent event) {
        System.out.println("Sending IN-APP notification to student " + event.getStudentId() + ": " + event.getMessage());
    }
}
