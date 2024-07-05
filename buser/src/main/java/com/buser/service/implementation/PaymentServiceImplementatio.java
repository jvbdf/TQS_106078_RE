package com.buser.service.implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buser.model.Payment;
import com.buser.model.PaymentStatus;
import com.buser.model.Reservation;
import com.buser.repository.PaymentRepository;
import com.buser.repository.ReservationRepository;

@Service
public class PaymentServiceImplementatio {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElse(null);
    }

    public Payment simulatePayment(Long reservationId, double amount) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        Payment payment = new Payment();
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setAmount(amount);
        payment.setPaymentTime(LocalDateTime.now());
        payment.setPaymentStatus(PaymentStatus.COMPLETED);
        payment.setReservation(reservation);

        return paymentRepository.save(payment);
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    public List<Payment> findPaymentsByTransactionId(String transactionId) {
        return paymentRepository.findByTransactionId(transactionId);
    }
    
}
