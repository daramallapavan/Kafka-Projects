package com.example.AngularProjectECOM.product;

import com.example.AngularProjectECOM.exception.ProductException;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product){

        product.setCreatedAt( LocalDateTime.now() );
        Product product1=productRepository.findByProductName(product.getProductName());
        if (product1!=null){
            throw new ProductException("product present in the database");
        }
        return productRepository.save( product);
    }


    public Product updateProduct(String productName,int quantity){
        Product product=productRepository.findByProductName(productName);
        if (product==null){
            throw new ProductException( "failed to update,product name is  not exist" );
        }

        product.setProductName( productName );
        product.setQuantity( quantity );
        return productRepository.save( product );
    }

    public Product getProduct(String productName) {

        if (productCache.getIfPresent( productName )!=null){
            Product product = productCache.getIfPresent( productName );

            System.out.println("returned from cache");
            return product;
        }
        Product product= productRepository.findByProductName( productName );
     productCache.put( product.getProductName(),product );

        System.out.println("returned from the database");
        return product;
    }



    private Cache<String,Product> productCache;

    private Cache<String,List<Product>> productListCache;
    @PostConstruct
    public void initCache(){
        productListCache= Caffeine.newBuilder()
                .maximumSize( 100 )
                .build();

        productCache= Caffeine.newBuilder()
                .maximumSize( 100 )
                .build();
    }




    public List<Product> getAllproducts() {
   /*     if (productListCache.getIfPresent( "products" )!=null){
            List<Product> products = productListCache.getIfPresent( "products" );
            System.out.println("products from Cache ...................");
            return products;
        }
*/
        List<Product> productList= productRepository.findAll();



        log.info( "product list {}",productList );

//        productListCache.put( "products",productList );
//        System.out.println("product from database");
        return productList;
    }
}
