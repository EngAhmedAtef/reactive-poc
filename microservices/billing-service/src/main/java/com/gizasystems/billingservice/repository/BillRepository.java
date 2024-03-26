package com.gizasystems.billingservice.repository;

import com.gizasystems.billingservice.entity.Bill;
import io.smallrye.mutiny.converters.uni.UniReactorConverters;
import lombok.RequiredArgsConstructor;
import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class BillRepository {
    private final Mutiny.SessionFactory sessionFactory;

    public Mono<Bill> createBill(Bill bill) {
        return sessionFactory.withTransaction(session -> session.persist(bill)
                        .replaceWith(bill))
                .convert().with(UniReactorConverters.toMono());
    }

    public Mono<Bill> updateBill(Bill bill) {
        return sessionFactory.withTransaction(session -> session.merge(bill)
                        .replaceWith(bill))
                .convert().with(UniReactorConverters.toMono());
    }

    public Flux<Bill> getUserBills(Long userId) {
        return sessionFactory.withSession(session -> session.createQuery("from Bill b where b.userId = :id", Bill.class)
                .setParameter("id", userId)
                .getResultList()
        ).convert().with(UniReactorConverters.toFlux()).flatMap(Flux::fromIterable);
    }

    public Mono<Bill> findBillById(Long billId) {
        return sessionFactory.withSession(session -> session.find(Bill.class, billId))
                .convert().with(UniReactorConverters.toMono());
    }
}
