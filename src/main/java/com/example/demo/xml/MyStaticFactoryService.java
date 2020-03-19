package com.example.demo.xml;

public class MyStaticFactoryService {
    private static MyStaticFactoryService service = new MyStaticFactoryService();

    public static MyStaticFactoryService createMyStaticFactoryService(){
        return service;
    }

    public void print(){
        System.out.println("hello MyStaticFactoryService");
    }

    private MyStaticFactoryService(){
        System.out.println("init MyStaticFactoryService...");
    }
}
