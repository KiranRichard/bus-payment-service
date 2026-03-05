package com.online.bus.ticket.reservation.payment.controller;

import com.online.bus.ticket.reservation.payment.model.Payment;
import com.online.bus.ticket.reservation.payment.request.PaymentRequest;
import com.online.bus.ticket.reservation.payment.service.PaymentService;
import com.online.bus.ticket.reservation.payment.validator.PaymentValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/api/payments")
@RestController
public class PaymentController {

    @Autowired
    private PaymentValidator paymentValidator;
    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public Payment createPayment(@RequestBody PaymentRequest paymentRequest) {
        log.info("Inside PaymentController createPayment Method");
        paymentValidator.validatePayment(paymentRequest);
        return paymentService.createPayment(paymentRequest);
    }

    @GetMapping("/{paymentId}")
    public Payment getPayment(@PathVariable("paymentId") long paymentId) {
        log.info("Inside PaymentController getPayment Method with paymentId: {}", paymentId);
        paymentValidator.validatePaymentId(paymentId);
        return paymentService.getPayment(paymentId);
    }

    @GetMapping()
    public List<Payment> getPayments() {
        log.info("Inside PaymentController getPayments Method");
        return paymentService.getPayments();
    }

    @PutMapping("/{paymentId}")
    public Payment editPayment(@RequestBody PaymentRequest paymentRequest, @PathVariable("paymentId") long paymentId) {
        log.info("Inside PaymentController editBusInventory Method with paymentId: {}", paymentId);
        paymentValidator.validatePaymentId(paymentId);
        paymentValidator.validatePayment(paymentRequest);
        return paymentService.editPayment(paymentRequest, paymentId);
    }

    @DeleteMapping("/{paymentId}")
    public void deletePayment(@PathVariable("paymentId") long paymentId) {
        log.info("Inside PaymentController deletePayment Method with paymentId: {}", paymentId);
        paymentValidator.validatePaymentId(paymentId);
        paymentService.deletePayment(paymentId);
    }

    @GetMapping("/booking-details-id/{bookingId}")
    public Payment getPaymentByBookingId(@PathVariable("bookingId") long bookingId) {
        log.info("Inside PaymentController getPaymentByBookingId Method with bookingId: {}", bookingId);
        paymentValidator.validateBookingId(bookingId);
        return paymentService.getPaymentByBookingId(bookingId);
    }
}
