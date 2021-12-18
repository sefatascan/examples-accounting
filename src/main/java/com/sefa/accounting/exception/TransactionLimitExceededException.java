package com.sefa.accounting.exception;

public class TransactionLimitExceededException extends RuntimeException {
    public TransactionLimitExceededException() {
        super("Transaction limit exceeded");
    }
}
