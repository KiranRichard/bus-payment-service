package com.online.bus.ticket.reservation.payment.validator;

import com.online.bus.ticket.reservation.payment.enums.PaymentStatus;
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

        if(paymentRequest.getBookingDetailsId()<=0) {
            log.info("[Error]: Invalid booking id is null in payment request");
            throw new RequiredFieldsMissingException("Invalid booking id is null in payment request");
        }

        if(paymentRequest.getAmount()<=0) {
            log.info("[Error]: Invalid amount is null in payment request");
            throw new RequiredFieldsMissingException("Invalid amount is null in payment request");
        }

        if(StringUtils.isBlank(paymentRequest.getPaymentStatus()) || Objects.isNull(PaymentStatus.findByName(paymentRequest.getPaymentStatus()))) {
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

    public void validateBookingDetailsId(long bookingDetailsId) {
        if(bookingDetailsId<=0) {
            log.info("[Error]: Invalid - booking details Id is null");
            throw new RequiredFieldsMissingException("Invalid - booking details Id is null");
        }
    }
}
