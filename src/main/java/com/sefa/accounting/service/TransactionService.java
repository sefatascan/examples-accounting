package com.sefa.accounting.service;

import com.sefa.accounting.dto.request.TransactionRequest;
import com.sefa.accounting.dto.response.TransactionDetailResponse;
import com.sefa.accounting.exception.TransactionLimitExceededException;
import com.sefa.accounting.model.Specialist;
import com.sefa.accounting.model.Transaction;
import com.sefa.accounting.model.TransactionStatus;
import com.sefa.accounting.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final SpecialistService specialistService;

    @Value("${application.transaction.limit}")
    private BigDecimal LIMIT;

    protected boolean limitCheck(BigDecimal amount, Specialist specialist) {
        Optional<BigDecimal> sumOfAmount = transactionRepository.sumOfAmountBySpecialistIdAndStatusIsSuccess(specialist.getId());
        int checked = sumOfAmount.stream().reduce(amount, BigDecimal::add).compareTo(LIMIT);
        return checked > 0 ? false : true;
    }

    public Page<TransactionDetailResponse> getTransactionsByStatus(TransactionStatus status, Pageable pageable) {
        return transactionRepository.findAllByStatus(status, pageable)
                .map(TransactionDetailResponse::mapper);
    }

    public TransactionDetailResponse createTransaction(TransactionRequest transactionRequest) {
        Specialist specialist = specialistService.findByUniqueConstraint(
                transactionRequest.getFirstName(),
                transactionRequest.getLastName(),
                transactionRequest.getEmail()
        );
        boolean status = limitCheck(transactionRequest.getAmount(), specialist);
        Transaction transaction = Transaction.builder()
                .specialist(specialist)
                .amount(transactionRequest.getAmount())
                .productName(transactionRequest.getProductName())
                .billNo(transactionRequest.getBillNo())
                .status(status ? TransactionStatus.SUCCESS : TransactionStatus.FAIL)
                .build();
        transactionRepository.save(transaction);

        if (!status) throw new TransactionLimitExceededException();

        return TransactionDetailResponse.mapper(transaction);
    }
}
