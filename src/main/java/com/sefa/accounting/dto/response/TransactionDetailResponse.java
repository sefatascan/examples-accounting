package com.sefa.accounting.dto.response;

import com.sefa.accounting.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionDetailResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private BigDecimal amount;
    private String productName;
    private String billNo;
    private String status;

    public static TransactionDetailResponse mapper(Transaction transaction) {
        return TransactionDetailResponse.builder()
                .id(transaction.getId())
                .firstName(transaction.getSpecialist().getFirstName())
                .lastName(transaction.getSpecialist().getLastName())
                .email(transaction.getSpecialist().getEmail())
                .amount(transaction.getAmount())
                .productName(transaction.getProductName())
                .billNo(transaction.getBillNo())
                .status(transaction.getStatus().name())
                .build();
    }
}
