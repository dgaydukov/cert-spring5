package com.example.spring5;


import com.example.logic.ann.aop.AopSimpleBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App{
    public static void main(String[] args) {
        /**
         * There are 2 ways we can add aspects
         * create bean and add them explicitly from config with ProxyFactoryBean
         * create them implicitly with @Aspect
         */
        var context = new AnnotationConfigApplicationContext("com.example.logic.ann.aop");
        context.getBean("originalBean", AopSimpleBean.class).print();
    }
}