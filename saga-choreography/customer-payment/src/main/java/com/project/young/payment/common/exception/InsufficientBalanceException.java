package com.project.young.payment.common.exception;

public class InsufficientBalanceException extends RuntimeException {

    private static final String MESSAGE = "Customer does not have enough balance";

    public InsufficientBalanceException() {
        super(MESSAGE);
    }
}
