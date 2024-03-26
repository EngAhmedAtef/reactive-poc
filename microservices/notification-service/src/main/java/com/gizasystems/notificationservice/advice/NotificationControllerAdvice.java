package com.gizasystems.notificationservice.advice;

import com.gizasystems.notificationservice.exception.NotificationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.Map;

@ControllerAdvice
public class NotificationControllerAdvice {
    @ExceptionHandler(NotificationNotFoundException.class)
    public Mono<ResponseEntity<Map<String, String>>> handleNotificationNotFound(NotificationNotFoundException exception) {
        Map<String, String> response = Map.of("message", exception.getMessage());
        return Mono.just(new ResponseEntity<>(response, HttpStatus.NOT_FOUND));
    }
}
