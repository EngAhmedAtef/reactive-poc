package com.gizasystems.activitylogservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ACTIVITY_TYPE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityType {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "ACTIVITY_NAME_EN")
    private String activityNameEn;
    @Column(name = "ACTIVITY_NAME_AR")
    private String activityNameAr;
}
