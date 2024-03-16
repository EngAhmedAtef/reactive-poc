package com.gizasystems.usermanagement.exception;

public class UserFieldException extends RuntimeException {
    public UserFieldException(String fieldName) {
        super(String.format("Couldn't find or access the field %s", fieldName));
    }
}
