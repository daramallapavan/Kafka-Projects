package com.example.delivery_microservice.dto;

import lombok.Data;

@Data
public class CustomerOrder {

    private String item;

    private int quantity;

    private double amount;

    private String paymentMode;

    private Long orderId;

    private String address;
}
