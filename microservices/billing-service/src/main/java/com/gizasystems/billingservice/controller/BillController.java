package com.gizasystems.billingservice.controller;

import com.gizasystems.billingservice.dto.BillDTO;
import com.gizasystems.billingservice.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("bills")
@RequiredArgsConstructor
public class BillController {
    private final BillService service;

    @PostMapping("create")
    public Mono<BillDTO> createBill(@RequestBody BillDTO billDTO) {
        return service.createBill(billDTO);
    }

    @GetMapping("users/{id}")
    public Flux<BillDTO> getUserBills(@PathVariable("id") Long userId) {
        return service.getUserBills(userId);
    }

    @PutMapping("users/{userId}/pay/{billId}")
    public Mono<BillDTO> payBill(@PathVariable("userId") Long userId, @PathVariable("billId") Long billId) {
        return service.payBill(userId, billId);
    }
}
