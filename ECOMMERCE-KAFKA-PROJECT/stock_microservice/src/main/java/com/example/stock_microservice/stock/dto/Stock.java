package com.example.stock_microservice.stock.dto;

import lombok.Data;

@Data
public class Stock {

    private String item;

    private int quantity;
}
