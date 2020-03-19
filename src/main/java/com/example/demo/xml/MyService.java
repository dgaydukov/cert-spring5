package com.example.demo.xml;

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
