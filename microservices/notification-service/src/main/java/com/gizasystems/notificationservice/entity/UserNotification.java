package com.gizasystems.notificationservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "USERS_NOTIFICATIONS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserNotification {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNotificationId;
    @Column(name = "CREATED_ON")
    private Timestamp createdOn;
    @Column(name = "USER_ID")
    private Long userId;
    @Column(name = "NOTIFICATION_ID")
    private Long notificationId;

    @PrePersist
    private void prePersist() {
        createdOn = new Timestamp(System.currentTimeMillis());
    }
}
