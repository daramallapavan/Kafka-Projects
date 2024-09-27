package com.example.AngularProjectECOM.user;

import com.example.AngularProjectECOM.exception.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserService userService;


    public Address createAddress(Address address,String email){
        User user=userService.getUserByEmail(email);
        if(user==null){
            throw new UserException("failed to create address,email not exist");
        }

        address.setUser(user);
        return addressRepository.save(address);


    }
}
