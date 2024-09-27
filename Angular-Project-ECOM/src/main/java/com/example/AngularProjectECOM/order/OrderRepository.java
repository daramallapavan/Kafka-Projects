package com.example.AngularProjectECOM.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Orders,Long> {
    Orders findByOrderNumber(String orderNumber);

    Orders findByOrderId(Long orderId);

    @Query("SELECT o From Orders o Where o.orderId=:orderId")
    Orders findOrderByOrderId(@Param( "orderId" ) Long orderId);
}
