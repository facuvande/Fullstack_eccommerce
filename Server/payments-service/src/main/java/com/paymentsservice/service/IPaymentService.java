package com.paymentsservice.service;

import com.paymentsservice.model.Payment;

public interface IPaymentService {
    public Payment createPayment(Payment payment);
    public Payment saveInformation(Long payment_id, Payment payment);
}
