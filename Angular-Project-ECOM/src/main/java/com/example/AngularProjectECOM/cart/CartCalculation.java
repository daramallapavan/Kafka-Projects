package com.example.AngularProjectECOM.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartCalculation {

    private double price;

    private long itemsCount;
}
