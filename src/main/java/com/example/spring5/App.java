package com.example.spring5;


import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext("com.example.logic.ann.misc");
    }
}