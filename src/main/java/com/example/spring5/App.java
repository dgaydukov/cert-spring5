package com.example.spring5;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.example.logic.ann.message.jms.MyJmsSender;

public class App{
    public static void main(String[] args) throws InterruptedException {
        var context = new AnnotationConfigApplicationContext("com.example.logic.ann.message.jms");
        MyJmsSender jms = context.getBean(MyJmsSender.class);
        jms.send();
        jms.send();
        System.out.println("done");
    }
}