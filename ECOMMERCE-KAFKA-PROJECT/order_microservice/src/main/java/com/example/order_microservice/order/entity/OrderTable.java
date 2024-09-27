package com.example.order_microservice.order.entity;


import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class OrderTable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String item;

    @Column
    private int quantity;

    @Column
    private float amount;

    @Column
    private String status;
}
