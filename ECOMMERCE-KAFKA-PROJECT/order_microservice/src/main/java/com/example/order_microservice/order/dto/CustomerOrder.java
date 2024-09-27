package com.example.order_microservice.order.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerOrder {

    private String item;

    private int quantity;

    private float amount;

    private String paymentMode;

    private long orderId;

    private String address;
}
