package com.gizasystems.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationDTO {
    private Long notificationId;
    private String notificationEn;
    private String notificationAr;
}
