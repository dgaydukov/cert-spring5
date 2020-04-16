package com.example.spring5;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

public class App {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext("com.example.spring5");
    }
}

@Component
class A{
    @Autowired
    public void setB(B b){
        System.out.println("setB => " + b);
    }
    @Autowired(required = false)
    public void setBFalse(B b){
        System.out.println("setBFalse => " + b);
    }
    @Autowired(required = false)
    public void setBoth(B b, C c){
        System.out.println("setBoth => " + b + ", " + c);
    }
}
@Component
class B{}
//@Component
class C{}