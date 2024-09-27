package com.example.AngularProjectECOM.order;

import com.example.AngularProjectECOM.cart.Cart;
import com.example.AngularProjectECOM.cart.CartItem;
import com.example.AngularProjectECOM.cart.CartItemRepository;
import com.example.AngularProjectECOM.cart.CartService;
import com.example.AngularProjectECOM.exception.OrderException;
import com.example.AngularProjectECOM.product.Product;
import com.example.AngularProjectECOM.product.ProductRepository;
import com.example.AngularProjectECOM.user.AddressRepository;
import com.example.AngularProjectECOM.user.User;
import com.example.AngularProjectECOM.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {



    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShippingAddressRepository shippingAddressRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductRepository productRepository;


    public Orders createOrderBySingleProduct(String email, String productName){
        User user= userRepository.findByEmail(email);

        Product product = productRepository.findByProductName( productName );

        List<OrderItem> orderItemsList=new ArrayList<>();



            OrderItem orderItem=new OrderItem();
            orderItem.setQuantity(1);
            orderItem.setPrice( product.getPrice() );
            orderItem.setUserId( user.getUserId() );
            orderItem.setProduct(product);
            OrderItem savedOrderItem= orderItemRepository.save( orderItem );

            orderItemsList.add(savedOrderItem);





        Orders createdOrders = Orders.builder()
                .orderStatus("PENDING")
                .orderNumber( UUID.randomUUID().toString())
                .totalItems(1)
                .totalPrice( product.getPrice() )
                .orderItems( orderItemsList )
                .paymentStatus( "FAILED" )
                .user( user)
                .createdAt( LocalDateTime.now() )
                .build();

        for (OrderItem orderItems : orderItemsList) {
            orderItems.setOrders( createdOrders );
        }

        Orders savedOrders =  orderRepository.save( createdOrders );
        return savedOrders;
    }


    public Orders createOrder(String email){
       User user= userRepository.findByEmail(email);


        Cart cart = user.getCart();


        List<CartItem> cartItems = cart.getCartItems();



        List<OrderItem> orderItemsList=new ArrayList<>();


        for (CartItem items:cartItems){
            OrderItem orderItem=new OrderItem();
            orderItem.setQuantity(items.getQuantity());
            orderItem.setPrice( items.getPrice() );
            orderItem.setUserId( user.getUserId() );
            orderItem.setProduct(items.getProduct());
           OrderItem savedOrderItem= orderItemRepository.save( orderItem );

           orderItemsList.add(savedOrderItem);

        }



        Orders createdOrders = Orders.builder()
                .orderStatus("PENDING")
                .orderNumber( UUID.randomUUID().toString())
                .totalItems(cart.getNumberOfItems())
                .totalPrice( cart.getTotalPrice() )
                .orderItems( orderItemsList )
                .paymentStatus( "FAILED" )
                .user( user)
                .createdAt( LocalDateTime.now() )
                .build();

        for (OrderItem orderItem : orderItemsList) {
            orderItem.setOrders( createdOrders );
        }

      Orders savedOrders =  orderRepository.save( createdOrders );

      return savedOrders;

    }



    public Orders getOrderByOrderNumber(String orderNumber){
        Orders orders = orderRepository.findByOrderNumber(orderNumber);
        if (orders ==null){
            throw new OrderException("failed to get order, orderNumber is not exist");
        }
        return orders;
    }


    public void createPaymentDuplicate(String orderNumber){


        Orders orders =getOrderByOrderNumber( orderNumber );
        User user= orders.getUser();
        if (orders.getPaymentStatus().equals( "FAILED")){
            orders.setPaymentStatus( "SUCCESS");
            if (orders.getPaymentStatus().equals( "SUCCESS" )){
                orders.setOrderStatus( "PLACED" );
                orderRepository.save( orders );

                if (orders.getOrderStatus().equals( "PLACED" )){

                    cartService.deleteCartItems( user.getEmail() );

                    cartService.createCart( user );

                }
            }

        }




    }

    public void createPaymentDuplicate(Long orderId){

        // Order order=orderRepository.findByOrderId( orderId );

        Orders orders =orderRepository.findOrderByOrderId(orderId);


        User user= orders.getUser();
        if (orders.getPaymentStatus().equals( "FAILED")){
            orders.setPaymentStatus( "SUCCESS");
            if (orders.getPaymentStatus().equals( "SUCCESS" )){
                orders.setOrderStatus( "PLACED" );
                orderRepository.save( orders );

                if (orders.getOrderStatus().equals( "PLACED" )){

                    cartService.deleteCartItems( user.getEmail() );

                    cartService.createCart( user );

                }
            }

        }




    }
    public List<Orders> getUserOrders(String email) {
       User user= userRepository.findByEmail( email );

       List<Orders> orders=user.getOrders();
       return orders;

    }




    public Orders createOrderWithAddress(String email, ShippingAddress shippingAddress) {
        User user = userRepository.findByEmail( email );


        List<CartItem> cartItems = cartService.getCartItems( email );


        List<OrderItem> ordersItemsList=new ArrayList<>();

        for (CartItem cartItem:cartItems){

            OrderItem orderItem=new OrderItem();

            orderItem.setPrice( cartItem.getPrice() );
            orderItem.setQuantity( cartItem.getQuantity() );
            orderItem.setProduct(  cartItem.getProduct());


            orderItem.setUserId( user.getUserId() );
            OrderItem savedOrderItems = orderItemRepository.save( orderItem );

            ordersItemsList.add( savedOrderItems );
        }

        double sum = cartItems.stream().mapToDouble( c -> c.getPrice() ).sum();

        Orders createdOrders = Orders.builder()
                .orderStatus("PENDING")
                .orderNumber( UUID.randomUUID().toString())
                .totalItems(cartItems.size())
                .totalPrice( sum )
                .orderItems( ordersItemsList )
                .paymentStatus( "FAILED" )
                .user( user)
                .createdAt( LocalDateTime.now() )
                .build();


        Orders savedOrder = orderRepository.save( createdOrders );

        shippingAddress.setOrders( savedOrder );

        shippingAddressRepository.save( shippingAddress );
        for (OrderItem ordersItems:ordersItemsList){
            ordersItems.setOrders( createdOrders );
          //  orderItemRepository.save( ordersItems );
        }



        return savedOrder;




    }
}
