package com.example.AngularProjectECOM.cart;

import com.example.AngularProjectECOM.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    CartItem findByCartAndProduct(Cart cart, Product product);

    CartItem findByCartItemId(Long cartItemId);

    CartItem findByProduct(Product product);
}
