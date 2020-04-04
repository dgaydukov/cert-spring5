package com.example.spring5;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.ThreadPoolExecutor;

public class App {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext("com.example.logic.ann.misc");
        var executor = new ThreadPoolExecutor()
    }
}