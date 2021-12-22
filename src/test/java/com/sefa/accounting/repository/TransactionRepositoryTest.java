package com.sefa.accounting.repository;

import com.sefa.accounting.model.Transaction;
import com.sefa.accounting.model.TransactionStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TransactionRepositoryTest extends EntityManagerHelper {

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void injectedComponentsAreNotNull() {
        assertThat(transactionRepository).isNotNull();
    }

    @Test
    void whenSuccessThen_sumOfAmountBySpecialistIdAndStatusIsSuccess() {
        Transaction transaction = createSuccessTransaction();

        Optional<BigDecimal> expected = transactionRepository
                .sumOfAmountBySpecialistIdAndStatusIsSuccess(transaction.getSpecialist().getId());

        assertThat(expected).isPresent();
        assertThat(expected.get()).isEqualTo(BigDecimal.valueOf(100.00).setScale(2));
    }

    @Test
    void whenFailThen_sumOfAmountBySpecialistIdAndStatusIsSuccess() {
        Transaction transaction = createFailTransaction();

        Optional<BigDecimal> expected = transactionRepository
                .sumOfAmountBySpecialistIdAndStatusIsSuccess(transaction.getSpecialist().getId());

        assertThat(expected).isNotPresent();
    }

    @Test
    void givenSuccessThen_findAllByStatus() {
        Transaction transaction = createSuccessTransaction();

        Page<Transaction> expected = transactionRepository.findAllByStatus(
                TransactionStatus.SUCCESS, PageRequest.of(0, 5));

        assertThat(expected.getContent()).isNotEmpty();
        assertThat(expected.getContent()).hasSize(1);

    }

    @Test
    void givenFailThen_findAllByStatus() {
        Transaction transaction = createFailTransaction();

        Page<Transaction> expected = transactionRepository.findAllByStatus(
                TransactionStatus.FAIL, PageRequest.of(0, 5));

        assertThat(expected.getContent()).isNotEmpty();
        assertThat(expected.getContent()).hasSize(1);
    }
}