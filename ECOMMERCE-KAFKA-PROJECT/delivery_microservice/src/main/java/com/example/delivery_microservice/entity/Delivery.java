package com.example.delivery_microservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String address;

    @Column
    private String status;

    @Column
    private long orderId;
}
