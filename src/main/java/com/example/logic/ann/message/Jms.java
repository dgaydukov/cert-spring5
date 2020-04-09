package com.example.logic.ann.message;

import javax.annotation.PostConstruct;
import javax.jms.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class Jms {
    @Autowired
    private JmsTemplate jms;

    @PostConstruct
    public void init(){
        jms.setDefaultDestinationName("localhost:61616");
    }

    public void send(){
        jms.send(session -> session.createObjectMessage("hello world"));
    }
}
