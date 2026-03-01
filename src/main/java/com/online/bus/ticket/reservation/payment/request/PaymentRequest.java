package com.online.bus.ticket.reservation.payment.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentRequest {

    private long bookingDetailsId;
    private double amount;
    private String paymentStatus;
    private LocalDateTime paymentDateTime;
}
