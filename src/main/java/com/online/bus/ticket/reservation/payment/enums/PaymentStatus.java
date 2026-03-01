package com.online.bus.ticket.reservation.payment.enums;

public enum PaymentStatus {

    IN_PROGRESS, SETTLED, DECLINED, REFUNDED;

    public static PaymentStatus findByName(String name) {
        PaymentStatus paymentStatus = null;
        for (PaymentStatus paymentStatusValue : values()) {
            if (paymentStatusValue.name().equalsIgnoreCase(name)) {
                paymentStatus = paymentStatusValue;
                break;
            }
        }
        return paymentStatus;
    }
}
