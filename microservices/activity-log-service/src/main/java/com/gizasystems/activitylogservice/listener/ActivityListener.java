package com.gizasystems.activitylogservice.listener;

import com.gizasystems.activitylogservice.enums.ActivityTypes;
import com.gizasystems.activitylogservice.service.ActivityService;
import com.gizasystems.common.model.CreateUserMessage;
import com.gizasystems.common.model.PaymentRequestMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ActivityListener {
    private final ActivityService service;

    @JmsListener(destination = "atef.css.user", selector = "event = '#{T(com.gizasystems.common.enums.Event).CREATE_USER.title()}'")
//    @JmsListener(destination = "atef.css.user", selector = "event='Create User'")
    private void userTopicCreateUserListener(CreateUserMessage createUserMessage) {
        log.info("CONSUMED A CREATE USER MESSAGE");
        service.logActivity(createUserMessage.getUserId(), ActivityTypes.CREATE_USER.id()).subscribe();
    }

    @JmsListener(destination = "atef.css.user", selector = "event = '#{T(com.gizasystems.common.enums.Event).PAY_BILL_REQUEST.title()}'")
    private void userTopicPayBillRequestListener(PaymentRequestMessage paymentRequestMessage) {
        log.info("CONSUMED A PAY BILL REQUEST MESSAGE");
        service.logActivity(paymentRequestMessage.getUserId(), ActivityTypes.PAYMENT_REQUEST.id()).subscribe();
    }

    @JmsListener(destination = "atef.css.bill", selector = "event = '#{T(com.gizasystems.common.enums.Event).PAY_BILL.title()}'")
    private void billActivityListener(PaymentRequestMessage paymentRequestMessage) {
        log.info("CONSUMED A PAY BILL MESSAGE");
        switch (paymentRequestMessage.getPaymentStatus()) {
            case SUCCESS -> service.logActivity(paymentRequestMessage.getUserId(), ActivityTypes.PAYMENT_SUCCESSFUL.id()).subscribe();
            case FAILURE -> service.logActivity(paymentRequestMessage.getUserId(), ActivityTypes.PAYMENT_FAILURE.id()).subscribe();
        }
    }
}
