package com.example.AngularProjectECOM.order;

import com.example.AngularProjectECOM.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "t_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private String orderNumber;


    private String orderStatus;

    private LocalDateTime createdAt;

    private double totalPrice;

    private int totalItems;

    private String paymentStatus;

    @OneToMany(mappedBy = "orders",cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;


    @OneToOne(mappedBy = "orders", cascade = CascadeType.ALL)
    private ShippingAddress shippingAddress;


    @ManyToOne
    @JsonIgnore
    private User user;


}
