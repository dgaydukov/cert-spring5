package com.example.logic.ann.misc;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

public class Parent {
    @PostConstruct
    private void init(){
        System.out.println("Parent");
    }
}

@Component
class Child extends Parent {
    @PostConstruct
    public void init(){
        System.out.println("Child");
    }
}
