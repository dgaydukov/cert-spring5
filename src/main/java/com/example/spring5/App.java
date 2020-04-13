package com.example.spring5;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.example.logic.ann.message.amqp.MyRabbitSender;

public class App{
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext("com.example.logic.ann.message.amqp");
        var sender = context.getBean(MyRabbitSender.class);
        sender.send();
        sender.send();
    }
}