package com.gizasystems.cssdb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "NOTIFICATIONS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;
    @Column(name = "NOTIFICATION_EN")
    private String notificationEn;
    @Column(name = "NOTIFICATION_AR")
    private String notificationAr;
    @OneToMany(mappedBy = "notification")
    private List<UserNotification> userNotification;
}

