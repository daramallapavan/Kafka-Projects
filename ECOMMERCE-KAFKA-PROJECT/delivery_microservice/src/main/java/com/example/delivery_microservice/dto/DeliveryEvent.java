package com.example.delivery_microservice.dto;

import lombok.Data;

@Data
public class DeliveryEvent {

    private String type;

    private CustomerOrder order;
}
