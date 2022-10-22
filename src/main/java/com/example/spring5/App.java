package com.example.spring5;

import com.example.logic.ann.postprocessors.LoggingWrapperBPP;
import com.example.logic.ann.postprocessors.logger.MyLogger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext("com.example.logic.ann.postprocessors");
        context.getBeanFactory().addBeanPostProcessor(new LoggingWrapperBPP());
        System.out.println("__START__");
        MyLogger printer = context.getBean(MyLogger.class);
        printer.m1();
        System.out.println();
        printer.m2();
    }
}