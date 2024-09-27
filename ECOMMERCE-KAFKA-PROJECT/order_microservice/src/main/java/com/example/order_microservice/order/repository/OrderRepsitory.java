package com.example.order_microservice.order.repository;

import com.example.order_microservice.order.entity.OrderTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepsitory extends JpaRepository<OrderTable,Long> {
}
