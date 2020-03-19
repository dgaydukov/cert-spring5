package com.example.demo.annotation;

import org.springframework.stereotype.Service;

@Service
public class MyService {
    private final MyStaticFactoryService service;

    public MyService(MyStaticFactoryService service){
        System.out.println("init MyService...");
        this.service = service;
    }
    public void print(){
        System.out.println("hello MyService");
        service.print();
    }
}
