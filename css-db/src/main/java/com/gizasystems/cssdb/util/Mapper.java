package com.gizasystems.cssdb.util;

import com.gizasystems.cssdb.dto.NotificationDTO;
import com.gizasystems.cssdb.dto.UserDTO;
import com.gizasystems.cssdb.entity.Notification;
import com.gizasystems.cssdb.entity.User;

public final class Mapper {

    public static UserDTO map(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getUsernameEn(),
                user.getUsernameAr(),
                user.getUserEmail(),
                user.getPassword(),
                user.getCreatedOn(),
                user.getModifiedOn()
        );
    }

    public static User map(UserDTO dto) {
        return new User(
                dto.getUserId(),
                dto.getUsernameEn(),
                dto.getUsernameAr(),
                dto.getUserEmail(),
                dto.getPassword(),
                dto.getCreatedOn(),
                dto.getModifiedOn(),
                null
        );
    }

    public static Notification map(NotificationDTO dto) {
        return new Notification(
                dto.getNotificationId(),
                dto.getNotificationEn(),
                dto.getNotificationAr(),
                null
        );
    }

    public static NotificationDTO map(Notification notification) {
        return new NotificationDTO(
                notification.getNotificationId(),
                notification.getNotificationEn(),
                notification.getNotificationAr()
        );
    }

}
