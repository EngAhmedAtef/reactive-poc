package com.gizasystems.usermanagement.dto;

import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO implements Serializable {
    private Long userId;
    private String usernameEn;
    private String usernameAr;
    private String userEmail;
    private String password;
    private Timestamp createdOn;
    private Timestamp modifiedOn;
}
