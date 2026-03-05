package com.online.bus.ticket.reservation.payment.request;

import lombok.Data;

@Data
public class InventoryUpdateRequest {
    private long bookingId;
    private long busRouteNum;
    private int noOfSeatsBooked;
}
