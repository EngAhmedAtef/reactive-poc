package com.gizasystems.usermanagement.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super(String.format("User with ID %d is not found.", id));
    }
}
