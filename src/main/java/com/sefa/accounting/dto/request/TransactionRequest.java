package com.sefa.accounting.dto.request;

import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
public class TransactionRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @DecimalMin(value = "0.0")
    @Digits(integer = 6, fraction = 2)
    private BigDecimal amount;

    @NotBlank
    private String productName;

    @NotBlank
    private String billNo;
}
