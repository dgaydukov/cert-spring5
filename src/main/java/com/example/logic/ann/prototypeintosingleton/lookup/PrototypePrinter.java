package com.example.logic.ann.prototypeintosingleton.lookup;

import javax.annotation.PostConstruct;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
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
