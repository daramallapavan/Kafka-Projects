package com.example.order_microservice.order.controller;


import com.example.order_microservice.order.dto.CustomerOrder;
import com.example.order_microservice.order.dto.OrderEvent;
import com.example.order_microservice.order.entity.OrderTable;
import com.example.order_microservice.order.repository.OrderRepsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderRepsitory orderRepsitory;

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;



    @PostMapping("/orders")
    public  void createOrder(@RequestBody CustomerOrder customerOrder){



        OrderTable order=new OrderTable();
        try {
            order.setAmount(customerOrder.getAmount());
            order.setItem(customerOrder.getItem());
            order.setQuantity(customerOrder.getQuantity());
            order.setStatus("CREATED");

            order = orderRepsitory.save(order);


            OrderEvent orderEvent = new OrderEvent();
            orderEvent.setOrder(customerOrder);
            orderEvent.setType("ORDER_CREATED");

            kafkaTemplate.send("new-orders",orderEvent);




        }catch (Exception e){
            order.setStatus("FAILED");
            orderRepsitory.save(order);

        }


    }
}
