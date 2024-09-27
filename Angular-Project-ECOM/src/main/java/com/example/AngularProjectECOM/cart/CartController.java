package com.example.AngularProjectECOM.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin("*")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/addToCartOld")
    public ResponseEntity<?> addToCartOld(@RequestParam String email, @RequestParam String productName){

        cartService.addToCartOld( email,productName );

        return new ResponseEntity<>( "item added to cart", HttpStatus.CREATED );
    }

    @PostMapping("/addToCart")
    public ResponseEntity<?> addToCart(@RequestParam String email, @RequestBody AddToCartRequest addToCartRequest){

        cartService.addToCart( email,addToCartRequest );

        return new ResponseEntity<>( "item added to cart", HttpStatus.CREATED );
    }



    @GetMapping("/getCartItems")
    public List<CartItem> getCartItems(@RequestParam String email){
        return cartService.getCartItems( email );
    }


    @DeleteMapping("/deleteCartItems")
    public ResponseEntity<?> deleteCartItems(@RequestParam String email){
         cartService.deleteCartItems( email );
        return new ResponseEntity<>( "deleted cart items", HttpStatus.OK );

    }

    @DeleteMapping("/deleteCartItem")
    public ResponseEntity<?> deleteCartItemByCartItemId(@RequestParam Long cartItemId){
        cartService.deleteCartItem(cartItemId);
        return new ResponseEntity<>( "item deleted successfully",HttpStatus.OK );
    }

    @GetMapping("/cart")
    public ResponseEntity<?> cartCalculationApi(String email){
         CartCalculation cartCalculation=cartService.getCartCalculation(email );
         return new ResponseEntity<>( cartCalculation,HttpStatus.OK );

    }
}
