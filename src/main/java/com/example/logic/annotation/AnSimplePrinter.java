package com.example.logic.annotation;

import javax.annotation.PostConstruct;

import com.example.logic.annotation.postprocessors.annotation.TransactionWrapper;
import com.example.logic.annotation.postprocessors.annotation.LoggingWrapper;
import org.springframework.stereotype.Service;

@Service
public class AnSimplePrinter implements Printer {
    @Override
    public void print(String str){
        System.out.println("printer => " + str);
    }

    @Override
    @LoggingWrapper
    public void print1(String str){
        System.out.println("printer1 => " + str);
    }

    @Override
    @TransactionWrapper
    public void print2(String str){
        System.out.println("printer2 => " + str);
    }



    @PostConstruct
    public void init3(){
        System.out.println("initializing AnSimplePrinter...");
    }
}