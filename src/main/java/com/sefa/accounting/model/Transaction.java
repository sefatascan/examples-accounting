package com.sefa.accounting.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Transaction extends AbstractEntity {

    @Column(scale = 2)
    private BigDecimal amount;

    private String productName;

    //TODO: fail olduğunda unique olmamalı
    @Column(unique = true, nullable = false)
    private String billNo;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialist_id", nullable = false)
    private Specialist specialist;
}
