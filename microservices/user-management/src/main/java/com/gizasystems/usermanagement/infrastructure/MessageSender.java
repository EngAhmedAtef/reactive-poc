package com.gizasystems.usermanagement.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageSender {
    private final JmsTemplate jmsTemplate;

    public void sendMessage(String destination, Object payload, String headerKey, String headerValue) {
        jmsTemplate.convertAndSend(destination, payload, message -> {
            message.setStringProperty(headerKey, headerValue);
            return message;
        });
        log.info(String.format("Published a message to %s", destination));
    }
}
