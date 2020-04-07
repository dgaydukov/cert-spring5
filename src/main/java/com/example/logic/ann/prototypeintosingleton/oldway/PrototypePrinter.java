package com.example.logic.ann.prototypeintosingleton.oldway;

import java.util.concurrent.ThreadLocalRandom;

public class PrototypePrinter {
    private int rand;

    public void init(){
        rand = ThreadLocalRandom.current().nextInt(99, 999);
    }

    public void print(String str){
        System.out.println(rand + " => " + str);
    }
}
