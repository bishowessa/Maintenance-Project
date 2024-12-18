package com.lms.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lms.persistence.Notification;
import com.lms.service.NotificationServiceImpl;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationServiceImpl notificationService;

    public NotificationController(NotificationServiceImpl notificationService) {
        this.notificationService = notificationService;
    }

    // Add a new notification
    @PostMapping
    public ResponseEntity<String> addNotification(@RequestBody Notification notification) {
        notificationService.addNotification(notification);
        return ResponseEntity.ok("Notification added successfully!");
    }

    // Get all notifications
    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    // Get read notifications
    @GetMapping("/read")
    public ResponseEntity<List<Notification>> getReadNotifications() {
        return ResponseEntity.ok(notificationService.getReadNotifications());
    }

    // Get unread notifications
    @GetMapping("/unread")
    public ResponseEntity<List<Notification>> getUnreadNotifications() {
        return ResponseEntity.ok(notificationService.getUnreadNotifications());
    }

    @GetMapping("/{userId}/all")
    public ResponseEntity<List<Notification>> getAllNotifications(@PathVariable String userId) {
        return ResponseEntity.ok(notificationService.getAllUserNotifications(userId));
    }

    @GetMapping("/{userId}/unread")
    public ResponseEntity<List<Notification>> getUnreadNotifications(@PathVariable String userId) {
        return ResponseEntity.ok(notificationService.getUserUnreadNotifications(userId));
    }

    @GetMapping("/{userId}/read")
    public ResponseEntity<List<Notification>> getReadNotifications(@PathVariable String userId) {
        return ResponseEntity.ok(notificationService.getUserReadNotifications(userId));
    }

    @PostMapping("/{notificationId}/mark-read")
    public ResponseEntity<Void> markNotificationAsRead(@PathVariable String notificationId) {
        notificationService.markNotificationAsRead(notificationId);
        return ResponseEntity.ok().build();
    }
}
