package com.buser.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.buser.model.Payment;
import com.buser.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment createPayment(Payment payment) {
        payment.setTransactionId(UUID.randomUUID());
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPaymentByTransactionId(UUID transactionId) {
        return paymentRepository.findByTransactionId(transactionId);
    }
}
