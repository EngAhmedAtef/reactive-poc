package com.gizasystems.activitylogservice.enums;

public enum ActivityTypes {
    PAYMENT_REQUEST(1L),
    PAYMENT_SUCCESSFUL(2L),
    PAYMENT_FAILURE(3L),
    CREATE_USER(4L);

    private final Long id;

    ActivityTypes(Long id) {
        this.id = id;
    }

    public Long id() { return id; }
}
