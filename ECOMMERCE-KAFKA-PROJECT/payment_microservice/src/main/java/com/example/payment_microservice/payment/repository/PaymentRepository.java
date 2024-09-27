package com.example.payment_microservice.payment.repository;

import com.example.payment_microservice.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {


    public List<Payment> findByOrderId(long orderId);
}
