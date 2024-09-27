package com.example.Kafka_Project_July.controller;

import com.example.Kafka_Project_July.dto.UserInformation;
import com.example.Kafka_Project_July.service.KafkaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kafka")
@RequiredArgsConstructor
public class KafkaProducerController {

    private final KafkaService kafkaService;



    @GetMapping("/send")
    public ResponseEntity<?> send(@RequestParam String message){

        try{
            kafkaService.send( message );

            return new ResponseEntity<>( "Messages Sent", HttpStatus.OK );
        }catch (Exception e){
            return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR )
                    .body( e.getMessage() );
        }
    }


}
