package com.example.AngularProjectECOM.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@CrossOrigin("*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Long> createOrder(@RequestParam String email){
        Orders orders =orderService.createOrder( email);
        return new ResponseEntity<>( orders.getOrderId(), HttpStatus.CREATED );
    }

    @PostMapping("/orderAddress")
    public ResponseEntity<Long> createOrderWithAddress(@RequestParam String email,@RequestBody ShippingAddress shippingAddress){
        Orders orders =orderService.createOrderWithAddress( email,shippingAddress);
        return new ResponseEntity<>( orders.getOrderId(), HttpStatus.CREATED );
    }
    @PostMapping("/buy")
    public ResponseEntity<Long> createOrderBy(@RequestParam String email,@RequestParam String productName){
        Orders orders =orderService.createOrderBySingleProduct( email, productName );
        return new ResponseEntity<>( orders.getOrderId(), HttpStatus.CREATED );
    }

    @GetMapping("/getOrder")
    public ResponseEntity<?> getOrderByOrderNumber(@RequestParam String orderNumber ){
        Orders orders =orderService.getOrderByOrderNumber(orderNumber);
        return new ResponseEntity<>( orders,HttpStatus.OK);
    }




    @GetMapping("/getUserOrders")
    public List<Orders> getUserOrders(@RequestParam String email){

        return orderService.getUserOrders(email);

    }
    @PutMapping("/payment")
    public ResponseEntity<?> donePayment(@RequestParam String orderNumber){
        orderService.createPaymentDuplicate( orderNumber );
        return new ResponseEntity<>( "Payment done,Order Placed Successfully",HttpStatus.OK );
    }


    @PutMapping("/paymentBy")
    public ResponseEntity<?> donePaymentByOrderId(@RequestParam Long orderId){
        orderService.createPaymentDuplicate( orderId );
        return new ResponseEntity<>( "Payment done,Order Placed Successfully",HttpStatus.OK );
    }

}
