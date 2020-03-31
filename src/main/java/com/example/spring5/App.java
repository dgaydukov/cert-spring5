package com.example.spring5;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.logic.ann.aop.AopSimpleBean;

public class App{
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("com.example.logic.ann");
        System.out.println();
        context.getBean("aopSimpleBean", AopSimpleBean.class).sayHello();
        System.out.println();
        context.getBean("originalAopSimpleBean", AopSimpleBean.class).sayHello();
    }
}