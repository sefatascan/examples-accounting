package com.sefa.accounting.service;

import com.sefa.accounting.dto.request.TransactionRequest;
import com.sefa.accounting.dto.response.TransactionDetailResponse;
import com.sefa.accounting.exception.TransactionLimitExceededException;
import com.sefa.accounting.model.Transaction;
import com.sefa.accounting.model.TransactionStatus;
import com.sefa.accounting.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private SpecialistService specialistService;

    @Mock
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(transactionService, "LIMIT", BigDecimal.valueOf(200.00));
    }

    @Test
    void limitCheck() {
        TransactionRequest request = TransactionServiceHelper.createTransactionRequest(BigDecimal.valueOf(100.00));
        Transaction transaction = TransactionServiceHelper.createTransaction(request, TransactionStatus.SUCCESS);
        Mockito.when(specialistService.findByUniqueConstraint(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail()))
                .thenReturn(transaction.getSpecialist());

        Mockito.when(transactionRepository.sumOfAmountBySpecialistIdAndStatusIsSuccess(transaction.getSpecialist().getId()))
                .thenReturn(Optional.of(BigDecimal.valueOf(50.00)));

        boolean expected = transactionService.limitCheck(transaction.getAmount(), transaction.getSpecialist());
        assertThat(expected).isTrue();

    }

    @Test
    void getTransactionsByStatus() {
    }

    @Test
    void createTransaction() {
        TransactionRequest request = TransactionServiceHelper.createTransactionRequest(BigDecimal.valueOf(100.00));

        Transaction transaction = TransactionServiceHelper.createTransaction(request, TransactionStatus.SUCCESS);


        Mockito.when(specialistService.findByUniqueConstraint(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail()))
                .thenReturn(transaction.getSpecialist());

        Mockito.when(transactionRepository.sumOfAmountBySpecialistIdAndStatusIsSuccess(transaction.getSpecialist().getId()))
                .thenReturn(Optional.of(BigDecimal.valueOf(50.00)));

        Mockito.when(transactionRepository.save(any())).thenReturn(transaction);

        TransactionDetailResponse expected = transactionService.createTransaction(request);

        assertThat(expected).isNotNull();
        assertThat(expected.getStatus()).isEqualTo(transaction.getStatus().name());

    }

    @Test
    void whenLimitExceedThenThrow_createTransaction() {
        TransactionRequest request = TransactionServiceHelper.createTransactionRequest(BigDecimal.valueOf(100.00));

        Transaction transaction = TransactionServiceHelper.createTransaction(request, TransactionStatus.SUCCESS);


        Mockito.when(specialistService.findByUniqueConstraint(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail()))
                .thenReturn(transaction.getSpecialist());

        Mockito.when(transactionRepository.sumOfAmountBySpecialistIdAndStatusIsSuccess(transaction.getSpecialist().getId()))
                .thenReturn(Optional.of(BigDecimal.valueOf(101.00)));

        assertThrows(TransactionLimitExceededException.class, () -> transactionService.createTransaction(request));

    }
}