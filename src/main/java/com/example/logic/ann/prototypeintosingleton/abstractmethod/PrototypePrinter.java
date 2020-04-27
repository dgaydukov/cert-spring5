package com.example.logic.ann.prototypeintosingleton.abstractmethod;

import javax.annotation.PostConstruct;
import java.util.concurrent.ThreadLocalRandom;

public class PrototypePrinter {
    private int rand;

    @PostConstruct
    public void init(){
        rand = ThreadLocalRandom.current().nextInt(99, 999);
    }

    public void print(String str){
        System.out.println(rand + " => " + str);
    }
}
