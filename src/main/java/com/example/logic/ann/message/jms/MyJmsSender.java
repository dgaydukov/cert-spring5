package com.example.logic.ann.message.jms;

import javax.jms.Message;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MyJmsSender {
    @Autowired
    private JmsTemplate jms;

    public void send(){
        jms.send(session -> session.createObjectMessage("time: " + LocalDateTime.now()));
    }

    /**
     * You can receive messages synchronously with this call
     */
    public Message receive(){
        return jms.receive();
    }
}
