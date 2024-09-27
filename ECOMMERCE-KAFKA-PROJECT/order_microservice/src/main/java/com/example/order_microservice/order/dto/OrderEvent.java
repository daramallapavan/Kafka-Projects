package com.example.order_microservice.order.dto;

import lombok.Data;

@Data
public class OrderEvent {

    private String type;

    private CustomerOrder order;
}
