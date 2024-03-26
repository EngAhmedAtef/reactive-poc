package com.gizasystems.billingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BillDTO {
    private Long id;
    private Double billAmount;
    private Long userId;
    private Boolean paid;
    private Timestamp createdOn;
}
