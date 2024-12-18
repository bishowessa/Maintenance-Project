package com.lms.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationEvent {
    private String studentId;
    private String message;
    private String notificationType;
}
