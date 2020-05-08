package com.example.logic.ann.aop;

public class AopSimpleBean {
    public String sayHello(){
        System.out.println("I'm AopSimpleBean");
        return "hello";
    }

    public void print(){
        System.out.println("printing...");
    }

    public String getName(){
        return "Mike";
    }
}
