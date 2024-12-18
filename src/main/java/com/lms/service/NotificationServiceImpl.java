package com.lms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lms.manager.NotificationManager;
import com.lms.persistence.Notification;

@Service
public class NotificationServiceImpl {

    private final NotificationManager notificationManager;

    public NotificationServiceImpl() {
        this.notificationManager = new NotificationManager();
    }

    // Add a new notification
    public void addNotification(Notification notification) {
        notificationManager.addNotification(notification);
    }

    // Get all notifications
    public List<Notification> getAllNotifications() {
        return notificationManager.getAllNotifications();
    }

    // Get read notifications
    public List<Notification> getReadNotifications() {
        return notificationManager.getReadNotifications();
    }

    // Get unread notifications
    public List<Notification> getUnreadNotifications() {
        return notificationManager.getUnreadNotifications();
    }
}
