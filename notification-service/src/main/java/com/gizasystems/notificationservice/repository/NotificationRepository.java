package com.gizasystems.notificationservice.repository;

import com.gizasystems.cssdb.dto.UserDTO;
import com.gizasystems.cssdb.entity.Notification;
import com.gizasystems.cssdb.entity.User;
import com.gizasystems.cssdb.entity.UserNotification;
import com.gizasystems.cssdb.util.Mapper;
import com.gizasystems.notificationservice.exception.NotificationNotFoundException;
import io.smallrye.mutiny.converters.uni.UniReactorConverters;
import lombok.RequiredArgsConstructor;
import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;

@Repository
@RequiredArgsConstructor
public class NotificationRepository {
    private final Mutiny.SessionFactory sessionFactory;
    private final WebClient webClient;

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

    // SetUserNotifications(userId, notificationId)
//    public Flux<Notification> getUserNotifications(Long userId) {
//        return sessionFactory.withSession(session ->
//                        session.createQuery("from Notification n left join fetch n.users u where u.userId = :id", Notification.class)
//                        .setParameter("id", userId)
//                        .getResultList()
//                ).convert().with(UniReactorConverters.toFlux()).flatMap(Flux::fromIterable);
//    }

    public Flux<Notification> getUserNotifications(Long userId) {
        return sessionFactory.withSession(session ->
                        session.createQuery("from Notification n left join fetch n.userNotification un where un.user.userId = :id", Notification.class)
                                .setParameter("id", userId)
                                .getResultList())
                .convert().with(UniReactorConverters.toFlux())
                .flatMap(Flux::fromIterable);
    }

    public Mono<Notification> setUserNotification(Long userId, Long notificationId) {
        return findById(notificationId)
                .switchIfEmpty(Mono.error(new NotificationNotFoundException(notificationId)))
                .flatMap(notification -> webClient.get()
                        .uri("http://localhost:8080/users/{id}", userId)
                        .retrieve()
                        .bodyToMono(UserDTO.class)
                        .switchIfEmpty(Mono.error(new RuntimeException("User not found")))
                        .flatMap(userDTO -> sessionFactory.withTransaction(session -> {
                            UserNotification userNotification = new UserNotification(
                                    null,
                                    new Timestamp(System.currentTimeMillis()),
                                    Mapper.map(userDTO),
                                    notification
                            );

                           return session.persist(userNotification)
                                    .chain(session::flush)
                                    .replaceWith(notification);
                        }).convert().with(UniReactorConverters.toMono())));
    }
}
