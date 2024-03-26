package com.gizasystems.notificationservice.exception;

public class NotificationNotFoundException extends RuntimeException {
    public NotificationNotFoundException(Long id) {
        super(String.format("Notification with ID %d not found.", id));
    }
}
