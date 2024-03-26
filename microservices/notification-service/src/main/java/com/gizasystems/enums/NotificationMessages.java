package com.gizasystems.enums;

public enum NotificationMessages {
    PAYMENT_REQUEST(1L),
    PAYMENT_SUCCESSFUL(2L),
    PAYMENT_FAILURE(3L),
    CREATE_USER(4L);

    private final Long id;

    NotificationMessages(Long id) {
        this.id = id;
    }


    public Long id() { return id; }
}
