package com.gizasystems.activitylogservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ActivityTypeDTO {
    private Long id;
    private String activityNameEn;
    private String activityNameAr;
}
