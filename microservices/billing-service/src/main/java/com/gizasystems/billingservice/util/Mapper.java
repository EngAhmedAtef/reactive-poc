package com.gizasystems.billingservice.util;

import com.gizasystems.billingservice.dto.BillDTO;
import com.gizasystems.billingservice.entity.Bill;

public final class Mapper {

    public static BillDTO map(Bill bill) {
        return new BillDTO(
                bill.getId(),
                bill.getBillAmount(),
                bill.getUserId(),
                bill.getPaid(),
                bill.getCreatedOn()
        );
    }

    public static Bill map(BillDTO dto) {
        return new Bill(
                dto.getId(),
                dto.getBillAmount(),
                dto.getUserId(),
                dto.getPaid(),
                dto.getCreatedOn()
        );
    }
}
