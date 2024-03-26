package com.gizasystems.usermanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long userId;
    @Column(name = "USERNAME_EN")
    private String usernameEn;
    @Column(name = "USERNAME_AR")
    private String usernameAr;
    @Column(name = "USER_EMAIL")
    private String userEmail;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "CREATED_ON")
    private Timestamp createdOn;
    @Column(name = "MODIFIED_ON")
    private Timestamp modifiedOn;

    @PrePersist
    private void prePersist() {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        createdOn = currentTimestamp;
        modifiedOn = currentTimestamp;
    }

    @PreUpdate
    private void preUpdate() {
        modifiedOn = new Timestamp(System.currentTimeMillis());
    }
}
