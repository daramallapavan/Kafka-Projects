package com.example.AngularProjectECOM.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@CrossOrigin("*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody Product product){
        productService.createProduct( product );
        return new ResponseEntity<>( "product created successfully", HttpStatus.CREATED );
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestParam String productName,@RequestParam int quantity){
        productService.updateProduct( productName, quantity);
        return new ResponseEntity<>( "product updated successfully", HttpStatus.CREATED );
    }

    @GetMapping("/getProduct")
    public Product getProductByProductId(@RequestParam  String productName){
       return productService.getProduct(productName);
    }

    @GetMapping("/getAllProducts")
    public List<Product> getAllProducts(){
        return productService.getAllproducts();
    }
}
