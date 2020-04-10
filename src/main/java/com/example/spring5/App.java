package com.example.spring5;

import com.example.logic.ann.postprocessors.beans.MyPrinter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App{
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext("com.example.logic.ann.postprocessors");
        context.getBean(MyPrinter.class).print();
    }
}


