package com.example.logic.ann.message.kafka;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MyKafkaSender {
    @Autowired
    private KafkaTemplate<String, String> kafka;

    public void send(){
        kafka.send(KafkaJavaConfig.TOPIC_NAME, "time: " + LocalDateTime.now());
    }
}