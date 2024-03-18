package com.gizasystems.notificationservice.service;

import com.gizasystems.cssdb.dto.NotificationDTO;
import com.gizasystems.cssdb.entity.Notification;
import com.gizasystems.cssdb.util.Mapper;
import com.gizasystems.notificationservice.exception.NotificationNotFoundException;
import com.gizasystems.notificationservice.repository.NotificationRepository;
import io.smallrye.mutiny.Uni;
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

    public Flux<NotificationDTO> getUserNotifications(Long userId) {
        return repository.getUserNotifications(userId).map(Mapper::map);
    }

    public Mono<NotificationDTO> setUserNotification(Long userId, Long notificationId) {
        return repository.setUserNotification(userId, notificationId).map(Mapper::map);
    }
}
