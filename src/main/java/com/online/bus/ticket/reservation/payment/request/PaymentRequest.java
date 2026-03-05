package com.online.bus.ticket.reservation.payment.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentRequest {

    private long bookingId;
    private long busRouteNum;
    private int noOfSeatsBooked;
    private double amount;
    private String paymentStatus;
    private LocalDateTime paymentDateTime;
}
