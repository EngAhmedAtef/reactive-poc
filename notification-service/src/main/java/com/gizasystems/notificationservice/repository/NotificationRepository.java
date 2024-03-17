package com.gizasystems.notificationservice.repository;

import com.gizasystems.cssdb.entity.Notification;
import io.smallrye.mutiny.converters.uni.UniReactorConverters;
import lombok.RequiredArgsConstructor;
import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class NotificationRepository {
    private final Mutiny.SessionFactory sessionFactory;

    public Mono<Notification> save(Notification notification) {
        return sessionFactory.withTransaction(session -> session.persist(notification)
                        .chain(session::flush)
                        .replaceWith(notification))
                .convert().with(UniReactorConverters.toMono());
    }
}
