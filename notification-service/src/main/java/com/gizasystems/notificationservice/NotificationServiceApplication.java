package com.gizasystems.notificationservice;

import com.gizasystems.cssdb.annotation.ImportCssDb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@ImportCssDb

@SpringBootApplication
public class NotificationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }
}
