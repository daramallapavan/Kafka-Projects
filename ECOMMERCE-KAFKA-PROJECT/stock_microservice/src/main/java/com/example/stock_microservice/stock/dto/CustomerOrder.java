package com.example.stock_microservice.stock.dto;

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
