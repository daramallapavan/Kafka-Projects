package com.example.payment_microservice.payment.dto;

import lombok.Data;

@Data
public class PaymentEvent {

    private String type;

    private CustomerOrder order;
}
