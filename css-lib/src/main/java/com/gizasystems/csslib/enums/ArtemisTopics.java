package com.gizasystems.csslib.enums;

public enum ArtemisTopics {
    USER_BILL("atef-user-bill"),
    USER_NOTIFICATION("atef-user-notification"),
    USER_ACTIVITY("atef-user-activity"),
    BILL_ACTIVITY("atef-bill-activity"),
    BILL_NOTIFICATION("atef-bill-notification");

    private final String topic;

    ArtemisTopics(String topic) {
        this.topic = topic;
    }

    public String topic() { return topic; }
}