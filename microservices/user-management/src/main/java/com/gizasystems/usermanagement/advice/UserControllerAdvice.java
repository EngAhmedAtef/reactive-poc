package com.gizasystems.usermanagement.advice;

import com.gizasystems.usermanagement.exception.CannotUpdateIdException;
import com.gizasystems.usermanagement.exception.UserFieldException;
import com.gizasystems.usermanagement.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    public Mono<ResponseEntity<Map<String, String>>> handleUserNotFoundException(UserNotFoundException exception) {
        return Mono.just(new ResponseEntity<>(Map.of("message", exception.getMessage()), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(value = {UserFieldException.class, CannotUpdateIdException.class})
    public Mono<ResponseEntity<Map<String, String>>> handleUserFieldException(RuntimeException exception) {
        return Mono.just(ResponseEntity.badRequest().body(Map.of("message", exception.getMessage())));
    }

}

