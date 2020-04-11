package com.example.spring5;

import com.example.logic.ann.beans.SimpleBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class App{
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext("com.example.logic.ann.beans");
        context.getBean(SimpleBean.class).sayHello();
    }
}