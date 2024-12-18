package com.lms.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.persistence.Notification;
import com.lms.service.NotificationServiceImpl;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationServiceImpl notificationService;

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
}
