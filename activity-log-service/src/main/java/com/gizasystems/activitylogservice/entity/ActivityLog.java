package com.gizasystems.activitylogservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "ACTIVITY_LOG")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ActivityLog {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "USER_ID")
    private Long userId;
    @Column(name = "ACTIVITY_ID")
    private Long activityId;
    @Column(name = "CREATED_ON")
    private Timestamp createdOn;

    @PrePersist
    private void prePersist() {
        createdOn = new Timestamp(System.currentTimeMillis());
    }
}
