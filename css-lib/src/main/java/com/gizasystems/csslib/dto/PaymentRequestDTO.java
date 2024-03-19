package com.gizasystems.csslib.dto;

import com.gizasystems.csslib.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDTO implements Serializable {
    private Long billId;
    private Long userId;
    private PaymentStatus paymentStatus;
}