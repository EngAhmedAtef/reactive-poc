package com.gizasystems.billingservice.service;

import com.gizasystems.billingservice.dto.BillDTO;
import com.gizasystems.billingservice.entity.Bill;
import com.gizasystems.billingservice.exception.BillNotFoundException;
import com.gizasystems.billingservice.repository.BillRepository;
import com.gizasystems.billingservice.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BillService {
    private final BillRepository repository;

    public Mono<BillDTO> createBill(BillDTO billDTO) {
        Bill bill = Mapper.map(billDTO);
        return repository.createBill(bill).map(Mapper::map);
    }

    public Mono<BillDTO> payBill(Long billId) {
        return repository.findBillById(billId)
                .switchIfEmpty(Mono.error(new BillNotFoundException(billId)))
                .flatMap(bill -> {
                    bill.setPaid(true);
                    return repository.updateBill(bill);
                }).map(Mapper::map);
    }

    public Flux<BillDTO> getUserBills(Long userId) {
        return repository.getUserBills(userId).map(Mapper::map);
    }
}
