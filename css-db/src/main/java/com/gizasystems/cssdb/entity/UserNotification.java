package com.gizasystems.cssdb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

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
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
    @ManyToOne
    @JoinColumn(name = "NOTIFICATION_ID")
    private Notification notification;
}
