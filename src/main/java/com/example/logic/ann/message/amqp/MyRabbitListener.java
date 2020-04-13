package com.example.logic.ann.message.amqp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class MyRabbitListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println("message => " + message);
    }
}