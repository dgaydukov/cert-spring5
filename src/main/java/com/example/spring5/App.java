package com.example.spring5;

import org.springframework.aop.support.AopUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.logic.ann.prototypeintosingleton.lookup.SingletonBean;

public class App{
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext("com.example.logic.ann.prototypeintosingleton.lookup");
        context.getBean(SingletonBean.class).sayHello();
        context.getBean(SingletonBean.class).sayHello();
    }
}