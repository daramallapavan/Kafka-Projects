package com.example.payment_microservice.payment.service;

import com.example.payment_microservice.payment.dto.CustomerOrder;
import com.example.payment_microservice.payment.dto.OrderEvent;
import com.example.payment_microservice.payment.dto.PaymentEvent;
import com.example.payment_microservice.payment.entity.Payment;
import com.example.payment_microservice.payment.repository.PaymentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ReversePayment {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @KafkaListener(topics = "reversed-payments", groupId = "payments-group")
    public void reversePayment(String event) {
        System.out.println("Inside reverse payment for order "+event);

        try {
            PaymentEvent paymentEvent = new ObjectMapper().readValue(event, PaymentEvent.class);

            CustomerOrder order = paymentEvent.getOrder();

            Iterable<Payment> payments = this.paymentRepository.findByOrderId(order.getOrderId());

            payments.forEach(p -> {
                p.setStatus("FAILED");
                paymentRepository.save(p);
            });

            OrderEvent orderEvent = new OrderEvent();
            orderEvent.setOrder(paymentEvent.getOrder());
            orderEvent.setType("ORDER_REVERSED");
            kafkaTemplate.send("reversed-orders", orderEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
