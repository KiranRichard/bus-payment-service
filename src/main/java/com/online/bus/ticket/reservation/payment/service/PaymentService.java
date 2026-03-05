package com.online.bus.ticket.reservation.payment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.bus.ticket.reservation.payment.enums.BookingStatus;
import com.online.bus.ticket.reservation.payment.exception.PaymentException;
import com.online.bus.ticket.reservation.payment.kafka.ProducerService;
import com.online.bus.ticket.reservation.payment.model.Payment;
import com.online.bus.ticket.reservation.payment.repository.PaymentRepository;
import com.online.bus.ticket.reservation.payment.request.InventoryUpdateRequest;
import com.online.bus.ticket.reservation.payment.request.PaymentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ProducerService producerService;
    @Autowired
    private ObjectMapper objectMapper;

    public Payment createPayment(PaymentRequest paymentRequest) throws JsonProcessingException {
        Payment payment = new Payment();
        payment.setBookingId(paymentRequest.getBookingId());
        payment.setAmount(paymentRequest.getAmount());
        payment.setPaymentStatus(BookingStatus.findByName(paymentRequest.getPaymentStatus()).name());
        payment.setBusRouteNum(paymentRequest.getBusRouteNum());
        payment.setNoOfSeatsBooked(paymentRequest.getNoOfSeatsBooked());
        payment.setPaymentDateTime(paymentRequest.getPaymentDateTime());

        Payment savedPayment = paymentRepository.save(payment);
        InventoryUpdateRequest inventoryUpdateRequest = new InventoryUpdateRequest();
        inventoryUpdateRequest.setBusRouteNum(savedPayment.getBusRouteNum());
        inventoryUpdateRequest.setNoOfSeatsBooked(savedPayment.getNoOfSeatsBooked());
        inventoryUpdateRequest.setBookingId(paymentRequest.getBookingId());
        String jsonMessage = objectMapper.writeValueAsString(inventoryUpdateRequest);
        producerService.sendMessageForUpdatePayments(jsonMessage);
        return savedPayment;
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
        payment.setBookingId(paymentRequest.getBookingId());
        payment.setAmount(paymentRequest.getAmount());
        payment.setPaymentStatus(BookingStatus.findByName(paymentRequest.getPaymentStatus()).name());
        payment.setBusRouteNum(paymentRequest.getBusRouteNum());
        payment.setNoOfSeatsBooked(payment.getNoOfSeatsBooked());
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

    public Payment getPaymentByBookingId(long bookingId) {
        Payment payment = paymentRepository.findByBookingId(bookingId).orElse(null);
        if (Objects.isNull(payment)){
            throw new PaymentException("Payment not present");
        }
        return payment;
    }
}
