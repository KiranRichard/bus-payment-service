package com.online.bus.ticket.reservation.payment.exception;

public class PaymentException extends RuntimeException {

    private String errorMessage;

    public PaymentException() {
        super();
    }

    public PaymentException(String errorMessage) {
        super(errorMessage);
    }
}
