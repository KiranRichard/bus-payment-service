package com.online.bus.ticket.reservation.payment.repository;

import com.online.bus.ticket.reservation.payment.model.Payment;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PaymentRepository extends CrudRepository<Payment, Long> {

    Optional<Payment> findByBookingId(long bookingId);
}
