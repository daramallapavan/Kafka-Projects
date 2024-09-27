package com.example.order_microservice.order.service;

import com.example.order_microservice.order.dto.OrderEvent;
import com.example.order_microservice.order.entity.OrderTable;
import com.example.order_microservice.order.repository.OrderRepsitory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ReverseOrder {

    @Autowired
    private OrderRepsitory orderRepsitory;

    @KafkaListener(topics = "reversed-orders",groupId = "orders-group")
    public  void reverseOrder(String event){


        try {
            OrderEvent orderEvent = new ObjectMapper().readValue(event, OrderEvent.class);

            Optional<OrderTable> order = orderRepsitory.findById(orderEvent.getOrder().getOrderId());

            order.ifPresent(
                    ord -> {
                        ord.setStatus("FAILED");

                        this.orderRepsitory.save(ord);
                    }
            );
        }catch(Exception e){
            e.printStackTrace();
        }



    }
}
