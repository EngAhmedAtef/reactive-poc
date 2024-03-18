package com.gizasystems.notificationservice.service;

import com.gizasystems.notificationservice.dto.NotificationDTO;
import com.gizasystems.notificationservice.dto.UserNotificationDTO;
import com.gizasystems.notificationservice.entity.Notification;
import com.gizasystems.notificationservice.entity.UserNotification;
import com.gizasystems.notificationservice.exception.NotificationNotFoundException;
import com.gizasystems.notificationservice.repository.NotificationRepository;
import com.gizasystems.notificationservice.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository repository;

    public Mono<NotificationDTO> createNotification(NotificationDTO notificationDTO) {
        Notification notification = Mapper.map(notificationDTO);
        return repository.createNotification(notification).map(Mapper::map);
    }

    public Flux<NotificationDTO> findAll() {
        return repository.findAll().map(Mapper::map);
    }

    public Mono<NotificationDTO> findById(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new NotificationNotFoundException(id)))
                .map(Mapper::map);
    }

    public Mono<UserNotificationDTO> setUserNotification(Long userId, Long notificationId) {
        UserNotification userNotification = new UserNotification(
                null,
                null,
                userId,
                notificationId
        );
        return repository.setUserNotification(userNotification).map(Mapper::map);
    }

    public Flux<NotificationDTO> getUserNotifications(Long userId) {
        return repository.getUserNotifications(userId).map(Mapper::map);
    }
}
