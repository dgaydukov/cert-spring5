package com.example.spring5;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.example.logic.ann.message.kafka.MyKafkaSender;

public class App{
    public static void main(String[] args) throws InterruptedException {
        var context = new AnnotationConfigApplicationContext("com.example.logic.ann.message.kafka");
        var sender = context.getBean(MyKafkaSender.class);
        sender.send();
        sender.send();
    }
}