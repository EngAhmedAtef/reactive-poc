package com.gizasystems.activitylogservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ActivityLogDTO {
    private Long id;
    private Long userId;
    private Long activityId;
    private Timestamp createdOn;
}
