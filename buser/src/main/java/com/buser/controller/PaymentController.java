package com.buser.controller;

import com.buser.model.Payment;
import com.buser.service.implementation.PaymentServiceImplementatio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentServiceImplementatio paymentService;

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        Payment payment = paymentService.getPaymentById(id);
        return payment != null ? ResponseEntity.ok(payment) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Payment> simulatePayment(@RequestBody SimulatePaymentRequest request) {
        return ResponseEntity.ok(paymentService.simulatePayment(request.getReservationId(), request.getAmount()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<List<Payment>> findPaymentsByTransactionId(@PathVariable String transactionId) {
        return ResponseEntity.ok(paymentService.findPaymentsByTransactionId(transactionId));
    }
}

class SimulatePaymentRequest {
    private Long reservationId;
    private double amount;

    // Getters and Setters
}
