package com.online.bus.ticket.reservation.payment.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.bus.ticket.reservation.payment.enums.BookingStatus;
import com.online.bus.ticket.reservation.payment.model.Payment;
import com.online.bus.ticket.reservation.payment.request.PaymentRequest;
import com.online.bus.ticket.reservation.payment.request.RefundRequest;
import com.online.bus.ticket.reservation.payment.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class ConsumerService {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PaymentService paymentService;

    @KafkaListener(topics = "booking-topic-insert", groupId = "admin-group")
    public void consumeMessageForInsertPayments(String message) throws JsonProcessingException {
        System.out.println("Received: " + message);

        PaymentRequest paymentRequest =
                objectMapper.readValue(message, PaymentRequest.class);
        paymentRequest.setPaymentStatus(BookingStatus.PAID.name());
        Payment payment = paymentService.createPayment(paymentRequest);
        if (Objects.isNull(payment)) {
            log.info("[Error] Message unable to process");
        }
        log.info("The message received: {} has been processed sucessfully.", message);
    }

    @KafkaListener(topics = "booking-topic-delete", groupId = "admin-group")
    public void consumeMessageForDeletePayments(String message) throws JsonProcessingException {
        System.out.println("Received: " + message);

        RefundRequest refundRequest = objectMapper.readValue(message, RefundRequest.class);
        Payment refundedPayment = paymentService.performRefunds(refundRequest);
        if (Objects.isNull(refundedPayment)) {
            log.info("[Error] Message unable to process");
        }
        log.info("The message received: {} has been processed sucessfully.", message);
    }
}
