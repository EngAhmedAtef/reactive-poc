package com.gizasystems.notificationservice.repository;

import com.gizasystems.notificationservice.entity.Notification;
import com.gizasystems.notificationservice.entity.UserNotification;
import io.smallrye.mutiny.converters.uni.UniReactorConverters;
import lombok.RequiredArgsConstructor;
import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class NotificationRepository {
    private final Mutiny.SessionFactory sessionFactory;

    public Mono<Notification> createNotification(Notification notification) {
        return sessionFactory.withTransaction(session -> session.persist(notification)
                        .chain(session::flush)
                        .replaceWith(notification))
                .convert().with(UniReactorConverters.toMono());
    }

    public Flux<Notification> findAll() {
        return sessionFactory.withSession(session -> session.createQuery("from Notification", Notification.class)
                        .getResultList()).convert().with(UniReactorConverters.toFlux())
                .flatMap(Flux::fromIterable);
    }

    public Mono<Notification> findById(Long id) {
        return sessionFactory.withSession(session -> session.find(Notification.class, id))
                .convert().with(UniReactorConverters.toMono())
                .log();
    }

    public Mono<UserNotification> setUserNotification(UserNotification userNotification) {
        return sessionFactory.withTransaction(session -> session.persist(userNotification)
                        .chain(session::flush)
                        .replaceWith(userNotification))
                .convert().with(UniReactorConverters.toMono());
    }

    public Flux<Notification> getUserNotifications(Long userId) {
        return sessionFactory.withSession(session ->
                        session.createQuery("from UserNotification un where un.userId = :id", UserNotification.class)
                                .setParameter("id", userId)
                                .getResultList()
                ).convert().with(UniReactorConverters.toFlux()).flatMap(Flux::fromIterable)
                .flatMap(userNotification -> findById(userNotification.getNotificationId()));
    }
}
