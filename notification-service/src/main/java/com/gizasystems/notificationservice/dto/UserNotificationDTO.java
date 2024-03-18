package com.gizasystems.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserNotificationDTO {
    private Long userNotificationId;
    private Timestamp createdOn;
    private Long userId;
    private Long notificationId;

}
