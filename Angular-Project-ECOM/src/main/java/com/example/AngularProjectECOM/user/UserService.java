package com.example.AngularProjectECOM.user;

import com.example.AngularProjectECOM.cart.CartItem;
import com.example.AngularProjectECOM.cart.CartService;
import com.example.AngularProjectECOM.exception.UserException;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartService cartService;


    public User createUser(UserDto userDto){

        User user=User.builder()
                .email(userDto.getEmail())
                .name(userDto.getName())
                .password(userDto.getPassword())
                .createdAt( LocalDateTime.now() )
                .build();


      User userExist= userRepository.findByEmail(userDto.getEmail());
      if(userExist==null){

          User savedUser= userRepository.save(user);
         cartService.createCart( savedUser );
          return savedUser;
      }
      else{
          throw new UserException("email exist, please try another email....");
      }


    }

    public List<User> getListOfAllUsers() {
        if (userListCache.getIfPresent( "users" )!=null){
           List<User>  listOfUsersCache= userListCache.getIfPresent( "users" );

            System.out.println("users from cache");
           return listOfUsersCache;
        }
        List<User> users= userRepository.findAll();
        userListCache.put( "users",users );
        System.out.println("users from database");
        return users;
    }

    private Cache<String,User> userCache;

    private Cache<String,List<User>> userListCache;
    @PostConstruct
    public void initCache(){
        userCache= Caffeine.newBuilder()
                .maximumSize( 100 )
                .build();


        userListCache= Caffeine.newBuilder()
                .maximumSize( 100 )
                .build();

    }

    public User getUserByEmail(String email) {
//        if (userCache.getIfPresent( "user" )!=null){
//            User user = userCache.getIfPresent( "user" );
//
//            System.out.println("get user from UserCache");
//            return user;
//        }
        User user =userRepository.findByEmail(email);
       // userCache.put( "user",user );
        System.out.println("get user from database");
        return user;
    }

    public void deleteUserByEmail(String email) {
        User user=userRepository.findByEmail(email);
        if (user==null){
            throw new UserException("failed to delete,email not exist");
        }

        userRepository.delete(user);
    }

    public User updateUserByEmail(String email,UserDto userDto) {
        User user=userRepository.findByEmail(email);
        if (user==null){
            throw new UserException("update failed,email not exist");
        }

        if (userDto.getName() == null) {
            user.setName(user.getName());
        } else {
            user.setName(userDto.getName());
        }

        if (userDto.getName()==null){
            user.setName(user.getName());
        }else{
            user.setName(userDto.getName());
        }
        if (userDto.getPassword()==null){
            user.setPassword(user.getPassword());
        }else{
            user.setPassword(userDto.getPassword());
        }

        return userRepository.save(user);
    }
}
