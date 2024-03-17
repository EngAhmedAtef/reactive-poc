package com.gizasystems.notificationservice;

import org.hibernate.reactive.mutiny.Mutiny;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class NotificationServiceApplicationTests {
    @Autowired
    private ApplicationContext context;

    @Test
    void check() {
        assertNotNull(context.getBean("sessionFactory"));
    }

}
