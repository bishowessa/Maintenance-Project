package com.lms.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.lms.persistence.Notification;

public class NotificationManager {
    private List<Notification> notifications;

    public NotificationManager() {
        this.notifications = new ArrayList<>();
    }

    // Add a notification
    public void addNotification(Notification notification) {
        notifications.add(notification);
        sendEmail(notification.getUserId(), "New Notification", notification.getMessage());
    }

    // Get all notifications
    public List<Notification> getAllNotifications() {
        return notifications;
    }

    // Get read notifications
    public List<Notification> getReadNotifications() {
        return notifications.stream()
            .filter(Notification::isRead)
            .collect(Collectors.toList());
    }

    // Get unread notifications
    public List<Notification> getUnreadNotifications() {
        return notifications.stream()
            .filter(notification -> !notification.isRead())
            .collect(Collectors.toList());
    }

    // Joseph
    private void sendEmail(String userId, String subject, String body) {
        // Replace this with actual email sending logic
        System.out.println("Sending email to user " + userId);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
    }
}
