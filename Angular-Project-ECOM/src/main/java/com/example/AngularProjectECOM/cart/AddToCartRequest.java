package com.example.AngularProjectECOM.cart;

import lombok.Data;

@Data
public class AddToCartRequest {

    private String productName;
    private int quantity;
    private int price;

}
