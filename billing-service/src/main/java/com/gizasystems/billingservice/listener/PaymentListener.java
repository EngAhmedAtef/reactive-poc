package com.gizasystems.billingservice.listener;


import com.gizasystems.billingservice.service.BillService;
import com.gizasystems.csslib.dto.PaymentRequestDTO;
import com.gizasystems.csslib.enums.ArtemisTopics;
import com.gizasystems.csslib.enums.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentListener {
    private final BillService service;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = "#{T(com.gizasystems.csslib.enums.ArtemisTopics).USER_BILL.topic()}")
    private void processPaymentListener(PaymentRequestDTO paymentRequestDTO) {
        if (paymentRequestDTO.getPaymentStatus() == PaymentStatus.INITIATED) {
            service.payBill(paymentRequestDTO.getBillId())
                    .doOnNext(billDTO -> paymentRequestDTO.setPaymentStatus(PaymentStatus.SUCCESS))
                    .doOnError(exception -> paymentRequestDTO.setPaymentStatus(PaymentStatus.FAILURE))
                    .doFinally(signalType ->  {
                        jmsTemplate.convertAndSend(ArtemisTopics.BILL_ACTIVITY.topic(), paymentRequestDTO);
                        jmsTemplate.convertAndSend(ArtemisTopics.BILL_NOTIFICATION.topic(), paymentRequestDTO);
                    })
                    .subscribe();
        }
    }
}
