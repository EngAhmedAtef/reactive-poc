package com.gizasystems.activitylogservice.repository;

import com.gizasystems.activitylogservice.entity.ActivityLog;
import com.gizasystems.activitylogservice.entity.ActivityType;
import io.smallrye.mutiny.converters.uni.UniReactorConverters;
import lombok.RequiredArgsConstructor;
import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ActivityRepository {
    private final Mutiny.SessionFactory sessionFactory;

    public Mono<ActivityType> createActivityType(ActivityType activityType) {
        return sessionFactory.withTransaction(session -> session.persist(activityType)
                        .replaceWith(activityType))
                .convert().with(UniReactorConverters.toMono());
    }

    public Flux<ActivityType> findAllActivityTypes() {
        return sessionFactory.withSession(session -> session.createQuery("from ActivityType", ActivityType.class)
                        .getResultList()).convert().with(UniReactorConverters.toFlux())
                .flatMap(Flux::fromIterable);
    }

    public Flux<ActivityType> findUserActivities(Long userId) {
        return sessionFactory.withSession(session ->
                        session.createQuery("select at.id, at.activityNameEn, at.activityNameAr from ActivityLog al left join ActivityType at on al.activityId = at.id where al.userId = :id", ActivityType.class)
                                .setParameter("id", userId)
                                .getResultList()
                ).convert().with(UniReactorConverters.toFlux())
                .flatMap(Flux::fromIterable);
    }

    public Mono<ActivityLog> logActivity(ActivityLog activityLog) {
        return sessionFactory.withTransaction(session -> session.persist(activityLog)
                        .replaceWith(activityLog))
                .convert().with(UniReactorConverters.toMono());
    }
}
