package com.sefa.accounting.service;

import com.sefa.accounting.dto.request.TransactionRequest;
import com.sefa.accounting.model.Specialist;
import com.sefa.accounting.model.Transaction;
import com.sefa.accounting.model.TransactionStatus;

import java.math.BigDecimal;

public class TransactionServiceHelper {

    public static TransactionRequest createTransactionRequest(BigDecimal amount) {
        TransactionRequest request = new TransactionRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john.doe@gmail.com");
        request.setAmount(amount);
        request.setProductName("testProduct");
        request.setBillNo("TR01");
        return request;
    }

    public static Specialist createSpecialist() {
        return Specialist.builder().id("specialist-id").build();
    }

    public static Transaction createTransaction(TransactionRequest request, TransactionStatus status) {
        return Transaction.builder()
                .id("transaction-id")
                .specialist(createSpecialist())
                .amount(request.getAmount())
                .productName(request.getProductName())
                .billNo(request.getBillNo())
                .status(status)
                .build();
    }
}
