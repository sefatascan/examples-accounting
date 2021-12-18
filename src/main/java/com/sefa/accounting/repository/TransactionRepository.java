package com.sefa.accounting.repository;

import com.sefa.accounting.model.Transaction;
import com.sefa.accounting.model.TransactionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

    @Query("select sum (t.amount) from Transaction t where t.specialist.id = :specialistId and t.status = 'SUCCESS'")
    Optional<BigDecimal> sumOfAmountBySpecialistIdAndStatusIsSuccess(String specialistId);

    Page<Transaction> findAllByStatus(TransactionStatus status, Pageable pageable);
}
