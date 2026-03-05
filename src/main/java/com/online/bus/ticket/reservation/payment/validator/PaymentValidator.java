package com.online.bus.ticket.reservation.payment.validator;

import com.online.bus.ticket.reservation.payment.enums.BookingStatus;
import com.online.bus.ticket.reservation.payment.exception.RequiredFieldsMissingException;
import com.online.bus.ticket.reservation.payment.request.PaymentRequest;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class PaymentValidator {

    public void validatePayment(PaymentRequest paymentRequest) {
        if (Objects.isNull(paymentRequest)) {
            log.info("[Error]: Invalid - payment request is null");
            throw new RequiredFieldsMissingException("Invalid - payment request is null");
        }

        if(paymentRequest.getBookingId()<=0) {
            log.info("[Error]: Invalid booking id is null in payment request");
            throw new RequiredFieldsMissingException("Invalid booking id is null in payment request");
        }

        if(paymentRequest.getBusRouteNum()<=0) {
            log.info("[Error]: Invalid bus route number is null in payment request");
            throw new RequiredFieldsMissingException("Invalid bus route number is null in payment request");
        }

        if(paymentRequest.getAmount()<=0) {
            log.info("[Error]: Invalid amount is null in payment request");
            throw new RequiredFieldsMissingException("Invalid amount is null in payment request");
        }

        if(StringUtils.isBlank(paymentRequest.getPaymentStatus()) || Objects.isNull(BookingStatus.findByName(paymentRequest.getPaymentStatus()))) {
            log.info("[Error]: Invalid payment status is null in payment request");
            throw new RequiredFieldsMissingException("Invalid payment status is null in payment request");
        }
    }

    public void validatePaymentId(long paymentId) {
        if(paymentId<=0) {
            log.info("[Error]: Invalid - payment Id is null");
            throw new RequiredFieldsMissingException("Invalid - payment Id is null");
        }
    }

    public void validateBookingId(long bookingId) {
        if(bookingId<=0) {
            log.info("[Error]: Invalid - booking Id is null");
            throw new RequiredFieldsMissingException("Invalid - booking Id is null");
        }
    }
}
