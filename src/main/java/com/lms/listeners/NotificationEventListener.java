package com.lms.listeners;

import com.lms.events.NotificationEvent;
import com.lms.persistence.OtpRequest;
import com.lms.persistence.User;
import com.lms.service.SmsService;
import com.lms.service.impl.EmailService;
import com.lms.service.impl.ServiceFacade;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import com.lms.service.NotificationServiceImpl;

import java.util.Optional;


@Component
@AllArgsConstructor
public class NotificationEventListener {

    private final NotificationServiceImpl notificationService;
    private final EmailService emailService;
    private final SmsService smsService;
    private final ServiceFacade service;

    @EventListener
    public void handleNotificationEvent(NotificationEvent event) {
        notificationService.saveNotification(event.getUserId(), event.getMessage());

        System.out.println("Notification Event Received:");
        System.out.println("Student ID: " + event.getUserId());
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
        String subject = "Notification for " + event.getUserId();
        User user = service.findUserById(event.getUserId());
        new Thread(() -> {
            emailService.sendEmail(user.getEmail(), subject, event.getMessage());
        }).start();
    }

    private void sendSMSNotification(NotificationEvent event) {
        User user = service.findUserById(event.getUserId());
        String lessonName= event.getMessage();
        OtpRequest otpRequest = new OtpRequest(
                "maya",
                "+201014367954",
                lessonName
        );
        new Thread(() -> {
            smsService.sendSMS(otpRequest, Optional.of(user));
        }).start();
        System.out.println("Sending SMS to student " + event.getUserId() + " for attendance otp of lesson : " + event.getMessage());
    }

    private void sendInAppNotification(NotificationEvent event) {
        System.out.println("Sending IN-APP notification to student " + event.getUserId() + ": " + event.getMessage());
    }
}
