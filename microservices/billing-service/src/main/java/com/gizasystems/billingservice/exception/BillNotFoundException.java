package com.gizasystems.billingservice.exception;

public class BillNotFoundException extends RuntimeException {
    public BillNotFoundException(Long id) {
        super(String.format("Bill with ID %d not found.", id));
    }
}
