package com.example.stock_microservice.stock.dto;

import lombok.Data;

@Data
public class PaymentEvent {

    private String type;

    private CustomerOrder order;
}
