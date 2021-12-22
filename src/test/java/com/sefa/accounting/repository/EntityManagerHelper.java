package com.sefa.accounting.repository;

import com.sefa.accounting.model.Specialist;
import com.sefa.accounting.model.Transaction;
import com.sefa.accounting.model.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;

@DataJpaTest
public class EntityManagerHelper {

    @Autowired
    private TestEntityManager testEntityManager;

    protected Specialist createSpecialist() {
        Specialist specialist = Specialist.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@gmail.com")
                .build();
        testEntityManager.persistAndFlush(specialist);
        return specialist;
    }

    protected Transaction createFailTransaction() {
        Specialist specialist = createSpecialist();

        Transaction transaction = Transaction.builder()
                .specialist(specialist)
                .amount(BigDecimal.valueOf(201.00))
                .productName("product")
                .billNo("TR01")
                .status(TransactionStatus.FAIL)
                .build();
        testEntityManager.persistAndFlush(transaction);
        return transaction;
    }

    protected Transaction createSuccessTransaction() {
        Specialist specialist = createSpecialist();

        Transaction transaction = Transaction.builder()
                .specialist(specialist)
                .amount(BigDecimal.valueOf(100.00))
                .productName("product")
                .billNo("TR01")
                .status(TransactionStatus.SUCCESS)
                .build();
        testEntityManager.persistAndFlush(transaction);
        return transaction;
    }

}
