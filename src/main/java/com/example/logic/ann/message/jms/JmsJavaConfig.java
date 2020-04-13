package com.example.logic.ann.message.jms;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

import org.apache.activemq.artemis.jms.client.ActiveMQJMSConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;

@Configuration
public class JmsJavaConfig {
    @Bean
    public Destination destination(){
        return new ActiveMQQueue("localhost:61616");
    }

    @Bean
    public ConnectionFactory connectionFactory(){
        return new ActiveMQJMSConnectionFactory();
    }

    @Bean
    public MessageConverter messageConverter(){
        return new SimpleMessageConverter();
    }

    @Bean
    public JmsTemplate jmsTemplate(){
        JmsTemplate jms = new JmsTemplate(connectionFactory());
        jms.setDefaultDestination(destination());
        return jms;
    }

    /**
     * You can also implicitly set listener by creating component with method annotated @JmsListener
     */
    @Bean
    public MessageListenerContainer listenerContainer() {
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setDestination(destination());
        container.setMessageListener(new MyJmsListener(messageConverter()));
        return container;
    }
}
