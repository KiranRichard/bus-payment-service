package com.online.bus.ticket.reservation.payment.service;

import com.online.bus.ticket.reservation.payment.enums.PaymentStatus;
import com.online.bus.ticket.reservation.payment.exception.PaymentException;
import com.online.bus.ticket.reservation.payment.model.Payment;
import com.online.bus.ticket.reservation.payment.repository.PaymentRepository;
import com.online.bus.ticket.reservation.payment.request.PaymentRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public Payment createPayment(PaymentRequest paymentRequest){
        Payment payment = new Payment();
        payment.setBookingDetailsId(paymentRequest.getBookingDetailsId());
        payment.setAmount(paymentRequest.getAmount());
        payment.setPaymentStatus(PaymentStatus.findByName(paymentRequest.getPaymentStatus()).name());
        payment.setPaymentDateTime(paymentRequest.getPaymentDateTime());
        return paymentRepository.save(payment);
    }

    public Payment getPayment(long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElse(null);
        if (Objects.isNull(payment)){
            throw new PaymentException("Payment not present");
        }
        return payment;
    }

    public Payment editPayment(PaymentRequest paymentRequest, long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElse(null);
        if (Objects.isNull(payment)){
            throw new PaymentException("Payment is not present and unable to update");
        }
        payment.setBookingDetailsId(paymentRequest.getBookingDetailsId());
        payment.setAmount(paymentRequest.getAmount());
        payment.setPaymentStatus(PaymentStatus.findByName(paymentRequest.getPaymentStatus()).name());
        payment.setPaymentDateTime(paymentRequest.getPaymentDateTime());
        return paymentRepository.save(payment);
    }

    public void deletePayment(long paymentId) {
        if (paymentRepository.findById(paymentId).isEmpty()) {
            throw new PaymentException("Payment is not present and unable to delete");
        }
        paymentRepository.deleteById(paymentId);
    }

    public List<Payment> getPayments() {
        return (List<Payment>) paymentRepository.findAll();
    }

    public Payment getPaymentByBookingDetailsId(long bookingDetailsId) {
        Payment payment = paymentRepository.findByBookingDetailsId(bookingDetailsId).orElse(null);
        if (Objects.isNull(payment)){
            throw new PaymentException("Payment not present");
        }
        return payment;
    }
}
