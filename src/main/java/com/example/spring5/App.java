package com.example.spring5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import com.example.logic.ann.message.Jms;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@ComponentScan("com.example.logic.ann.message")
public class App{
    public static void main(String[] args) {
        var context = SpringApplication.run(App.class, args);
        System.out.println("context => " + context.getClass().getName());
        context.getBean(Jms.class).send();
    }
}