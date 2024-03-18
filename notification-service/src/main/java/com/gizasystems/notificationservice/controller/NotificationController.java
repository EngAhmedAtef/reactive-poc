package com.gizasystems.notificationservice.controller;

import com.gizasystems.cssdb.dto.NotificationDTO;
import com.gizasystems.cssdb.entity.Notification;
import com.gizasystems.notificationservice.service.NotificationService;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService service;

    @GetMapping
    public Flux<NotificationDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("{id}")
    public Mono<NotificationDTO> findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @PostMapping("create")
    public Mono<NotificationDTO> createNotification(@RequestBody NotificationDTO notificationDTO) {
        return service.createNotification(notificationDTO);
    }

    @GetMapping("users/{id}")
    public Flux<NotificationDTO> getUserNotifications(@PathVariable("id") Long userID) {
        return service.getUserNotifications(userID);
    }

    @PostMapping("users/create/{userId}/{notificationId}")
    public Mono<NotificationDTO> setUserNotification(@PathVariable("userId") Long userId, @PathVariable("notificationId") Long notificationId) {
        return service.setUserNotification(userId, notificationId);
    }
}
