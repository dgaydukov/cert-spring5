package com.example.logic.ann.message.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.jms.support.converter.MessageConverter;

public class MyJmsListener implements MessageListener {
    private MessageConverter converter;

    public MyJmsListener(MessageConverter converter){
        this.converter = converter;
    }

    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("converted => " + converter.fromMessage(message) + ", message => " + message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
