package com.gizasystems.billingservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.sql.Timestamp;

@Entity
@Table(name = "BILL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "BILL_AMOUNT")
    private Double billAmount;
    @Column(name = "USER_ID")
    private Long userId;
    @Column(name = "PAID")
    private Boolean paid;
    @Column(name = "CREATED_ON")
    private Timestamp createdOn;

    @PrePersist
    private void prePersist() {
        createdOn = new Timestamp(System.currentTimeMillis());
    }
}
