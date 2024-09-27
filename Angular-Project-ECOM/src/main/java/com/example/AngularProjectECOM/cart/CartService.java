package com.example.AngularProjectECOM.cart;

import com.example.AngularProjectECOM.exception.ProductException;
import com.example.AngularProjectECOM.exception.UserException;
import com.example.AngularProjectECOM.product.Product;
import com.example.AngularProjectECOM.product.ProductRepository;
import com.example.AngularProjectECOM.user.User;
import com.example.AngularProjectECOM.user.UserRepository;
import com.example.AngularProjectECOM.user.UserService;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.annotation.PostConstruct;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;



    public Cart createCart(User user){
        Cart cart=new Cart();
        cart.setUser( user );
        return cartRepository.save( cart );
    }


    public void addToCart(String email ,AddToCartRequest addToCartRequest){
      User user=  userRepository.findByEmail( email );

      if (user==null){
          throw new UserException( " failed to add item,email is not exist" );
      }



      Cart cart=user.getCart();
      if(cart==null){
          throw new ProductException( "cart is empty,please try again later" );
      }

      Product product=productRepository.findByProductName(addToCartRequest.getProductName());

      if (product==null){
          throw new ProductException( "failed to add item,productName not exist" );
      }
        List<CartItem> cartItemList=new ArrayList<>();

      CartItem isPresent=cartItemRepository.findByCartAndProduct(cart,product);
      if (isPresent==null){
          CartItem cartItem = new CartItem();
          cartItem.setCart(cart);
          cartItem.setProduct(product);
          cartItem.setUserId(user.getUserId());
          cartItem.setQuantity(1);
          cartItem.setPrice( product.getPrice() *cartItem.getQuantity());

          CartItem savedCartItem = cartItemRepository.save( cartItem );
          cartItemList.add( savedCartItem );
      }

//
//        if(isPresent.getProduct().getProductName().equals( addToCartRequest.getProductName() )){
//            throw new ProductException( "product already exist in the cart" );
//        }


        double totalPrice = cart.getCartItems().stream().mapToDouble( cr -> cr.getPrice() ).sum();

        int noOfItems=cart.getCartItems().size();

        cart.setNumberOfItems( noOfItems );
        cart.setTotalPrice( totalPrice );
        cart.setCartItems( cartItemList );
        cart.setUser( user );
        cartRepository.save( cart );

    }


    public void addToCartOld(String email ,String productName){
        User user=  userRepository.findByEmail( email );

        if (user==null){
            throw new UserException( " failed to add item,email is not exist" );
        }



        Cart cart=user.getCart();
        if(cart==null){
            throw new ProductException( "cart is empty,please try again later" );
        }

        Product product=productRepository.findByProductName(productName);

        if (product==null){
            throw new ProductException( "failed to add item,productName not exist" );
        }
        List<CartItem> cartItemList=new ArrayList<>();

        CartItem isPresent=cartItemRepository.findByCartAndProduct(cart,product);
        if (isPresent==null){
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setUserId(user.getUserId());
            cartItem.setQuantity(1);
            cartItem.setPrice( product.getPrice() *cartItem.getQuantity());

            CartItem savedCartItem = cartItemRepository.save( cartItem );
            cartItemList.add( savedCartItem );
        }


        double totalPrice = cart.getCartItems().stream().mapToDouble( cr -> cr.getPrice() ).sum();

        int noOfItems=cart.getCartItems().size();

        cart.setNumberOfItems( noOfItems );
        cart.setTotalPrice( totalPrice );
        cart.setCartItems( cartItemList );
        cart.setUser( user );
        cartRepository.save( cart );

    }


    /*private Cache<String,List<CartItem>> cartItemListCache;
    @PostConstruct
    public void initCache(){
        cartItemListCache= Caffeine.newBuilder()
                .maximumSize( 100 )
                .build();

    }*/


    public List<CartItem> getCartItems(String email){
//        if (cartItemListCache.getIfPresent( "CartItemList" )!=null){
//            List<CartItem> cartItemList = cartItemListCache.getIfPresent( "CartItemList" );
//            System.out.println("get cart from cache ...............");
//            return cartItemList;
//        }else{
            User user=userRepository.findByEmail( email );
            if (user==null){
                throw new UserException( "failed to get cart items,email not exist" );
            }

            Cart cart=user.getCart();
            if (cart==null){
                throw new ProductException( "There is no item present in the cart" );
            }
            List<CartItem> cartItemList= cart.getCartItems();

//            cartItemListCache.put( "CartItemList",cartItemList );
//            System.out.println("get cart from database.............");

            return cartItemList;
    //    }

    }

    public CartCalculation getCartCalculation(String email){
       List<CartItem> cartItems= getCartItems( email );
      double totalCartPrice= cartItems.stream().mapToDouble( cartItem -> cartItem.getPrice() ).sum();
        long totalItems=cartItems.stream().mapToLong( cart->cart.getCartItemId() ).count();

        CartCalculation cartCalculation=new CartCalculation();
        cartCalculation.setPrice( totalCartPrice );
        cartCalculation.setItemsCount( totalItems );
        return cartCalculation;
    }


    public void deleteCartItems(String email){


    User user=  userRepository.findByEmail( email );

  Cart cart=  user.getCart();

       cartRepository.delete(cart);

      // cartItemListCache.invalidate( "CartItemList" );

    }

    public void deleteCartItem(Long cartItemId) {


        cartItemRepository.deleteById( cartItemId );

        System.out.println("cart item deleted successfully "+cartItemId);


      //  cartItemListCache.invalidate( "CartItemList" );
    }
}
