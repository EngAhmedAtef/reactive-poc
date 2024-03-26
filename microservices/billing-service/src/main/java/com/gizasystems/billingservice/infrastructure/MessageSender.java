package com.gizasystems.billingservice.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageSender {
    private final JmsTemplate jmsTemplate;

    public void sendMessage(String destination, Object payload, String headerKey, String headerValue) {
        jmsTemplate.convertAndSend(destination, payload, message -> {
            message.setStringProperty(headerKey, headerValue);
            return message;
        });
    }
}
