package com.gizasystems.common.enums;

public enum Event {
    CREATE_USER("Create User"),
    PAY_BILL_REQUEST("Pay bill request"),
    PAY_BILL("Pay bill");

    private final String title;

    Event(String title) {
        this.title = title;
    }

    public String title() { return title; }
}
