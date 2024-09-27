package com.example.Kafka_Project_July.service;

import com.example.Kafka_Project_July.dto.UserInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class KafkaService {

    @Value( "${spring.kafka.topic}" )
    private String topicName;

    private  final  KafkaTemplate<String,Object> kafkaTemplate;





    public void send(String message){

        for (int i=1;i<5000;i++){

           // CompletableFuture<SendResult<String, Object>> send =
                    kafkaTemplate.send( "SpringBoot-Topic", message + i );

          /*  send.whenComplete( (result,ex)->{
                if (ex==null){
                    //System.out.println("Messages Sent Success OffSet :"+result.getRecordMetadata().offset());
                }else{
                    System.out.println("Failed due to ."+ex.getMessage());
                }
            } );*/
        }
    }





}
