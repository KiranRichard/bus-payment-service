package com.online.bus.ticket.reservation.payment.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RefundRequest {
    private long bookingId;
    private long busRouteNum;
    private String reasonForCancellation;
    private LocalDateTime refundedDateTime;
}
