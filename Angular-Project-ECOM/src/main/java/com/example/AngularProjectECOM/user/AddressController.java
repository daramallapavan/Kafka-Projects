package com.example.AngularProjectECOM.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/create")
    public ResponseEntity<?> createAddress(@RequestParam String email, @RequestBody Address address){
        Address address1 = addressService.createAddress( address, email );
        return new ResponseEntity<>("address created with email "+address1.getUser().getEmail(), HttpStatus.CREATED );
    }
}
