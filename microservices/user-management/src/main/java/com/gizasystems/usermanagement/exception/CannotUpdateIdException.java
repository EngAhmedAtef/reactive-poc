package com.gizasystems.usermanagement.exception;

public class CannotUpdateIdException extends RuntimeException {
    public CannotUpdateIdException() {
        super("Updating the ID is not allowed.");
    }
}
