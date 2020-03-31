package com.example.spring5;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.logic.ann.props.Person1;
import com.example.logic.ann.props.Person2;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("com.example.logic.ann.props");
        System.out.println(context.getBean(Person1.class));
        System.out.println(context.getBean(Person2.class));
    }
}