package com.gizasystems.notificationservice.util;

import com.gizasystems.notificationservice.dto.UserNotificationDTO;
import com.gizasystems.notificationservice.entity.Notification;
import com.gizasystems.notificationservice.dto.NotificationDTO;
import com.gizasystems.notificationservice.entity.UserNotification;

public final class Mapper {
    public static Notification map(NotificationDTO dto) {
        return new Notification(
                dto.getNotificationId(),
                dto.getNotificationEn(),
                dto.getNotificationAr()
        );
    }

    public static NotificationDTO map(Notification notification) {
        return new NotificationDTO(
                notification.getNotificationId(),
                notification.getNotificationEn(),
                notification.getNotificationAr()
        );
    }

    public static UserNotificationDTO map(UserNotification userNotification) {
        return new UserNotificationDTO(
                userNotification.getUserNotificationId(),
                userNotification.getCreatedOn(),
                userNotification.getUserId(),
                userNotification.getNotificationId()
        );
    }

    public static UserNotification map(UserNotificationDTO dto) {
        return new UserNotification(
                dto.getUserNotificationId(),
                dto.getCreatedOn(),
                dto.getUserId(),
                dto.getNotificationId()
        );
    }
}
