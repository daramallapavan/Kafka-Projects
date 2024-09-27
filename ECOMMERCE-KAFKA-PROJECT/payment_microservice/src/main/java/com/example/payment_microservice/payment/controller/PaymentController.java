package com.example.payment_microservice.payment.controller;

import com.example.payment_microservice.payment.dto.CustomerOrder;
import com.example.payment_microservice.payment.dto.OrderEvent;
import com.example.payment_microservice.payment.dto.PaymentEvent;
import com.example.payment_microservice.payment.entity.Payment;
import com.example.payment_microservice.payment.repository.PaymentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private KafkaTemplate<String, PaymentEvent> paymentEventKafkaTemplate;


    @Autowired
    private KafkaTemplate<String, OrderEvent> orderEventKafkaTemplate;


    @KafkaListener(topics = "new-orders",groupId = "orders-group")
    public void processPayment(String event) throws JsonProcessingException {

            OrderEvent orderEvent = new ObjectMapper().readValue(event, OrderEvent.class);

            CustomerOrder order = orderEvent.getOrder();

        Payment payment=new Payment();

        try{


            payment.setAmount(order.getAmount());
            payment.setMode(order.getPaymentMode());
            payment.setOrderId(order.getOrderId());
            payment.setStatus("SUCCESS");
            paymentRepository.save(payment);


                PaymentEvent paymentEvent=new PaymentEvent();
                paymentEvent.setOrder(orderEvent.getOrder());
                paymentEvent.setType("PAYMENT_CREATED");

                paymentEventKafkaTemplate.send("new-payments", paymentEvent);
        }catch(Exception e){
                payment.setOrderId(order.getOrderId());
                payment.setStatus("FAILED");
                paymentRepository.save(payment);

                OrderEvent oe = new OrderEvent();
                oe.setOrder(order);
                oe.setType("ORDER_REVERSED");
                orderEventKafkaTemplate.send("reversed-orders", orderEvent);
        }

    }
}
