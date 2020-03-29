package com.example.logic.ann;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.logic.ann.postprocessors.annotation.PostAppReady;

@Service
public class SimpleBean {
    @Value("goodBean")
    private String name;
    @Autowired
    private Printer printer;


    public SimpleBean() {
        System.out.println("constructing SimpleBean...");
    }

    @PostConstruct
    public void init1() {
        System.out.println("initializing SimpleBean, phase 1...");
    }

    public void init2() {
        System.out.println("initializing SimpleBean, phase 2...");
    }

    @PostAppReady
    public void init3() {
        System.out.println("initializing SimpleBean, phase 3...");
    }

    public void sayHello() {
        printer.print("I'm SimpleBean, my name is " + name);
    }
}