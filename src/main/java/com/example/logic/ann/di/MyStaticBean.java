package com.example.logic.ann.di;

import org.springframework.stereotype.Component;

@Component
public class MyStaticBean {
    private MyStaticBean(){
        System.out.println("private constructor");
    }

    public static MyStaticBean getInstance(){
        System.out.println("getInstance");
        return new MyStaticBean();
    }

    public void sayHello(){
        System.out.println("hello");
    }
}
