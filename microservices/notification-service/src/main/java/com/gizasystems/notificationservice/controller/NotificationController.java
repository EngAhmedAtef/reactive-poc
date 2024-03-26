package com.gizasystems.notificationservice.controller;

import com.gizasystems.notificationservice.dto.NotificationDTO;
import com.gizasystems.notificationservice.dto.UserNotificationDTO;
import com.gizasystems.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService service;

    @PostMapping("create")
    public Mono<NotificationDTO> createNotification(@RequestBody NotificationDTO notificationDTO) {
        return service.createNotification(notificationDTO);
    }

    @GetMapping
    public Flux<NotificationDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("{id}")
    public Mono<NotificationDTO> findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @GetMapping("users/{id}")
    public Flux<NotificationDTO> getUserNotifications(@PathVariable("id") Long userID) {
        return service.getUserNotifications(userID);
    }
}
