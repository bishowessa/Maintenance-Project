package com.lms.manager;

import com.lms.persistence.Notification;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Scope("singleton")
public class NotificationManager {
    private final List<Notification> notifications = new ArrayList<>();

    // Add a notification
    public void addNotification(Notification notification) {
        notifications.add(notification);
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

    public List<Notification> findByUserId(String userId) {
        return notifications.stream()
                .filter(notification -> notification.getUserId() != null && notification.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public List<Notification> findUnreadByUserId(String userId) {
        return notifications.stream()
                .filter(notification -> notification.getUserId() != null && notification.getUserId().equals(userId) && !notification.isRead())
                .collect(Collectors.toList());
    }

    public List<Notification> findReadByUserId(String userId) {
        return notifications.stream()
                .filter(notification -> notification.getUserId() != null && notification.getUserId().equals(userId) && notification.isRead())
                .collect(Collectors.toList());
    }

    public void markAsRead(String notificationId) {
        notifications.stream()
                .filter(notification -> notification.getId().equals(notificationId))
                .findFirst()
                .ifPresent(notification -> notification.setRead(true));
    }


}