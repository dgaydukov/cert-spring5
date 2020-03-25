package com.example.spring.annotation;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AnSimpleBean {
    @Value("goodBean")
    private String name;
    @Autowired
    private Printer printer;

    public AnSimpleBean(){
        System.out.println("constructing SimpleBean...");
    }

    @PostConstruct
    public void init(){
        System.out.println("initializing SimpleBean...");
    }

    public void init2(){
        System.out.println("GOT IT");
    }

    public void print(){
        printer.print("I'm SimpleBean, my name is " + name);
    }
}