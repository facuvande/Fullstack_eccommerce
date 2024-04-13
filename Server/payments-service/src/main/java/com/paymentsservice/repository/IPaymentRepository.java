package com.paymentsservice.repository;

import com.paymentsservice.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByCollectionId(String collectionId);
}
