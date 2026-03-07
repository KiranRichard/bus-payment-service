package com.online.bus.ticket.reservation.payment.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessageForUpdatePayments(String message) {
        kafkaTemplate.send("payment-topic-update", message);
    }

    public void sendMessageForDeletePayments(String message) {
        kafkaTemplate.send("payment-topic-delete", message);
    }

    public void sendMessageForRejectPayments(String message) {
        kafkaTemplate.send("payment-topic-reject", message);
    }
}
