package com.example.AngularProjectECOM.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto){
        User user = userService.createUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers(){
        List<User> users=userService.getListOfAllUsers();
        if (users.size()==0){
            return new ResponseEntity<>("there is no users yet created",HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(users,HttpStatus.OK);
        }

    }


    @GetMapping("/getUserByEmail")
    public ResponseEntity<?> getUserByEmail(@RequestParam String email){
        User user=userService.getUserByEmail(email);
        if (user==null){
            return new ResponseEntity<>("no user present with this email "+email,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUserByEmail(@RequestParam String email){
        userService.deleteUserByEmail(email);

        return new ResponseEntity<>("user deleted with this email "+email,HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUserByEmail(@RequestParam String email,@RequestBody UserDto userDto){

        User user = userService.updateUserByEmail(email, userDto);
        return new ResponseEntity<>("user updated successfully ",HttpStatus.CREATED);
    }
}
