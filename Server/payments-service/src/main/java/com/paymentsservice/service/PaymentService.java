package com.paymentsservice.service;

import com.paymentsservice.model.Payment;
import com.paymentsservice.repository.IPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService implements IPaymentService{

    @Autowired
    IPaymentRepository paymentRepository;

    @Override
    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Payment saveInformation(Long payment_id, Payment payment) {
        Payment myPayment = paymentRepository.findById(payment_id).orElse(null);
        if(myPayment != null){
            payment.setPayment_id(payment_id);
            return paymentRepository.save(payment);
        };
        return null;
    }
}
