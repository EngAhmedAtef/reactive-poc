package com.gizasystems.notificationservice.listener;

import com.gizasystems.csslib.dto.PaymentRequestDTO;
import com.gizasystems.enums.NotificationMessages;
import com.gizasystems.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationListener {
    private final NotificationService service;

    @JmsListener(destination = "#{T(com.gizasystems.csslib.enums.ArtemisTopics).USER_NOTIFICATION.topic()}")
    private void userNotificationListener(PaymentRequestDTO paymentRequestDTO) {
        handleMessage(paymentRequestDTO);
    }

    @JmsListener(destination = "#{T(com.gizasystems.csslib.enums.ArtemisTopics).BILL_NOTIFICATION.topic()}")
    private void billNotificationListener(PaymentRequestDTO paymentRequestDTO) {
        handleMessage(paymentRequestDTO);
    }

    private void handleMessage(PaymentRequestDTO paymentRequestDTO) {
        switch (paymentRequestDTO.getPaymentStatus()) {
            case INITIATED -> service.setUserNotification(paymentRequestDTO.getUserId(), NotificationMessages.PAYMENT_REQUEST.id()).subscribe();
            case SUCCESS -> service.setUserNotification(paymentRequestDTO.getUserId(), NotificationMessages.PAYMENT_SUCCESSFUL.id()).subscribe();
            case FAILURE -> service.setUserNotification(paymentRequestDTO.getUserId(), NotificationMessages.PAYMENT_FAILURE.id()).subscribe();
        }
    }
}
