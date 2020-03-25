package com.example.spring.annotation;

import com.example.spring.annotation.postprocessors.ExceptionLogger;
import com.example.spring.annotation.postprocessors.TimeLogger;
import org.springframework.stereotype.Service;

@Service
public class AnSimplePrinter implements Printer {
    @Override
    public void print(String str){
        System.out.println("printer => " + str);
    }

    @Override
    @TimeLogger
    public void print1(String str){
        System.out.println("printer1 => " + str);
    }

    @Override
    @ExceptionLogger
    public void print2(String str){
        System.out.println("printer2 => " + str);
    }
}