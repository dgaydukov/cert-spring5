package com.example.logic.ann.beans;

import com.example.logic.ann.postprocessors.annotation.TransactionWrapper;
import com.example.logic.ann.postprocessors.annotation.LoggingWrapper;
import org.springframework.stereotype.Service;

@Service
public class SimplePrinter implements Printer {
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
}