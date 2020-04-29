package com.example.logic.ann.aop;

import org.apache.kafka.common.protocol.types.Field;

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
