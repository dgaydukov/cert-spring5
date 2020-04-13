package com.example.logic.ann.message.amqp;

import java.time.LocalDateTime;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyRabbitSender {
    @Autowired
    private RabbitTemplate rabbit;

    public void send() {
        MessageConverter converter = rabbit.getMessageConverter();
        MessageProperties properties = new MessageProperties();
        Message message = converter.toMessage("time: " + LocalDateTime.now(), properties);
        rabbit.send(RabbitJavaConfig.QUEUE_NAME, message);
    }
}