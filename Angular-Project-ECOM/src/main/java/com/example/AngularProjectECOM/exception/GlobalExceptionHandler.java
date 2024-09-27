package com.example.AngularProjectECOM.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<String> handleUserException(UserException userException){
        return new ResponseEntity<>(userException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }



    @ExceptionHandler(ProductException.class)
    public ResponseEntity<String> handleProductException(ProductException productException){
        return new ResponseEntity<>(productException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<String> handleOrderException(OrderException orderException){
        return new ResponseEntity<>(orderException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
