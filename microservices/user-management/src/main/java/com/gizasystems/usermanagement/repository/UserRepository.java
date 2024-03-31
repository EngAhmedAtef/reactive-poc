package com.gizasystems.usermanagement.repository;

import com.gizasystems.usermanagement.entity.User;
import io.smallrye.mutiny.converters.uni.UniReactorConverters;
import lombok.RequiredArgsConstructor;
import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final Mutiny.SessionFactory sessionFactory;

    public Mono<User> createUser(User user) {
        return sessionFactory.withTransaction(session -> session.persist(user)
                        .replaceWith(user))
                .convert().with(UniReactorConverters.toMono());
    }

    public Flux<User> findAll(String keyword, Pageable pageable) {
        String query;
        if (keyword != null && !keyword.isEmpty() && !keyword.isBlank())
            query = "from User u where u.usernameEn ilike :keyword";
        else
            query = "from User";
        return sessionFactory.withSession(session -> {
                    Mutiny.SelectionQuery<User> selectQuery = session.createQuery(query, User.class);
                    if (keyword != null && !keyword.isEmpty() && !keyword.isBlank())
                        selectQuery.setParameter("keyword", "%" + keyword + "%");

                    return selectQuery
                            .setFirstResult(pageable.getPageNumber())
                            .setMaxResults(pageable.getPageSize())
                            .getResultList();
                })
                .convert().with(UniReactorConverters.toFlux())
                .flatMap(Flux::fromIterable);
    }

    public Mono<User> findById(Long id) {
        return sessionFactory.withSession(session -> session.find(User.class, id))
                .convert().with(UniReactorConverters.toMono());
    }

    public Mono<User> update(User user) {
        return sessionFactory.withTransaction(session -> session.merge(user)
                        .replaceWith(user))
                .convert().with(UniReactorConverters.toMono());
    }
}
