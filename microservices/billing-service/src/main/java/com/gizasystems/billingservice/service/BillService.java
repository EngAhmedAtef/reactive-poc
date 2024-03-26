package com.gizasystems.billingservice.service;

import com.gizasystems.billingservice.config.JmsConfig;
import com.gizasystems.billingservice.dto.BillDTO;
import com.gizasystems.billingservice.entity.Bill;
import com.gizasystems.billingservice.exception.BillNotFoundException;
import com.gizasystems.billingservice.infrastructure.MessageSender;
import com.gizasystems.billingservice.repository.BillRepository;
import com.gizasystems.billingservice.util.Mapper;
import com.gizasystems.common.dto.PaymentRequestMessage;
import com.gizasystems.common.enums.Event;
import com.gizasystems.common.enums.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BillService {
    private final BillRepository repository;
    private final MessageSender messageSender;

    public Mono<BillDTO> createBill(BillDTO billDTO) {
        Bill bill = Mapper.map(billDTO);
        return repository.createBill(bill).map(Mapper::map);
    }

    public Mono<BillDTO> payBill(Long userId, Long billId) {
        return repository.findBillById(billId)
                .flatMap(bill -> {
                    if (bill == null) {
                        messageSender.sendMessage("atef.css.bill", new PaymentRequestMessage(billId, userId, PaymentStatus.FAILURE), "event", Event.PAY_BILL.title());
                        return Mono.error(new BillNotFoundException(billId));
                    }
                    bill.setPaid(true);
                    Mono<Bill> result = repository.updateBill(bill);
                    messageSender.sendMessage("atef.css.bill", new PaymentRequestMessage(billId, userId, PaymentStatus.SUCCESS), "event", Event.PAY_BILL.title());
                    return result;
                }).map(Mapper::map);
    }

    public Flux<BillDTO> getUserBills(Long userId) {
        return repository.getUserBills(userId).map(Mapper::map);
    }
}
