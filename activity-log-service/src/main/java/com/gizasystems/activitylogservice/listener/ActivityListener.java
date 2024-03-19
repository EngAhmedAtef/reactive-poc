package com.gizasystems.activitylogservice.listener;

import com.gizasystems.activitylogservice.enums.ActivityTypes;
import com.gizasystems.activitylogservice.service.ActivityService;
import com.gizasystems.csslib.dto.PaymentRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActivityListener {
    private final ActivityService service;

    @JmsListener(destination = "#{T(com.gizasystems.csslib.enums.ArtemisTopics).USER_ACTIVITY.topic()}")
    private void userActivityListener(PaymentRequestDTO paymentRequestDTO) {
        handleMessage(paymentRequestDTO);
    }

    @JmsListener(destination = "#{T(com.gizasystems.csslib.enums.ArtemisTopics).BILL_ACTIVITY.topic()}")
    private void billActivityListener(PaymentRequestDTO paymentRequestDTO) {
        handleMessage(paymentRequestDTO);
    }

    private void handleMessage(PaymentRequestDTO paymentRequestDTO) {
        switch (paymentRequestDTO.getPaymentStatus()) {
            case INITIATED -> service.logActivity(paymentRequestDTO.getUserId(), ActivityTypes.PAYMENT_REQUEST.id()).subscribe();
            case SUCCESS -> service.logActivity(paymentRequestDTO.getUserId(), ActivityTypes.PAYMENT_SUCCESSFUL.id()).subscribe();
            case FAILURE -> service.logActivity(paymentRequestDTO.getUserId(), ActivityTypes.PAYMENT_FAILURE.id()).subscribe();
        }
    }
}
