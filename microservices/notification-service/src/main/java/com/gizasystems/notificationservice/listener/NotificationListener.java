package com.gizasystems.notificationservice.listener;

import com.gizasystems.common.model.CreateUserMessage;
import com.gizasystems.common.model.PaymentRequestMessage;
import com.gizasystems.enums.NotificationMessages;
import com.gizasystems.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationListener {
    private final NotificationService service;

    @JmsListener(destination = "atef.css.user", selector = "event = '#{T(com.gizasystems.common.enums.Event).CREATE_USER.title()}'")
    private void userNotificationCreateUserListener(CreateUserMessage createUserMessage) {
        log.info("CONSUMED A CREATE USER MESSAGE");
        service.setUserNotification(createUserMessage.getUserId(), NotificationMessages.CREATE_USER.id()).subscribe();
    }

    @JmsListener(destination = "atef.css.user", selector = "event = '#{T(com.gizasystems.common.enums.Event).PAY_BILL_REQUEST.title()}'")
    private void userNotificationPayBillRequestListener(PaymentRequestMessage paymentRequestMessage) {
        service.setUserNotification(paymentRequestMessage.getUserId(), NotificationMessages.PAYMENT_REQUEST.id()).subscribe();
    }

    @JmsListener(destination = "atef.css.bill", selector = "event = '#{T(com.gizasystems.common.enums.Event).PAY_BILL.title()}'")
    private void billNotificationPayBillListener(PaymentRequestMessage paymentRequestMessage) {
        switch (paymentRequestMessage.getPaymentStatus()) {
            case SUCCESS -> service.setUserNotification(paymentRequestMessage.getUserId(), NotificationMessages.PAYMENT_SUCCESSFUL.id()).subscribe();
            case FAILURE -> service.setUserNotification(paymentRequestMessage.getUserId(), NotificationMessages.PAYMENT_FAILURE.id()).subscribe();
        }
    }
}
