package com.lms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lms.manager.NotificationManager;
import com.lms.persistence.Notification;

@Service
public class NotificationServiceImpl {

    private final NotificationManager notificationManager;

    public NotificationServiceImpl(NotificationManager notificationManager) {
        this.notificationManager = notificationManager;
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

    public void saveNotification(String userId, String message) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setMessage(message);
        notificationManager.addNotification(notification);
    }

    public List<Notification> getAllUserNotifications(String userId) {
        return notificationManager.findByUserId(userId);
    }

    public List<Notification> getUserUnreadNotifications(String userId) {
        return notificationManager.findUnreadByUserId(userId);
    }

    public List<Notification> getUserReadNotifications(String userId) {
        return notificationManager.findReadByUserId(userId);
    }

    public void markNotificationAsRead(String notificationId) {
        notificationManager.markAsRead(notificationId);
    }
}
