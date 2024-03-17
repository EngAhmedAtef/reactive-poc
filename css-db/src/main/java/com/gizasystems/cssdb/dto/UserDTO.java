package com.gizasystems.cssdb.dto;

import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {
    private Long userId;
    private String usernameEn;
    private String usernameAr;
    private String userEmail;
    private String password;
    private Timestamp createdOn;
    private Timestamp modifiedOn;
}