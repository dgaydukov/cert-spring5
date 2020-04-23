package com.example.spring5;
//2-27

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App{
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(App.class.getPackageName());
    }
}

