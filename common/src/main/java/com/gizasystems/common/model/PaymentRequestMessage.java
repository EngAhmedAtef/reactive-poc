package com.gizasystems.common.model;

import com.gizasystems.common.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestMessage implements Serializable {
    private Long billId;
    private Long userId;
    private PaymentStatus paymentStatus;
}