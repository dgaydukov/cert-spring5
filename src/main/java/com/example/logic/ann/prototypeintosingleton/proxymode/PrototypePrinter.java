package com.example.logic.ann.prototypeintosingleton.proxymode;

import javax.annotation.PostConstruct;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PrototypePrinter {
    public PrototypePrinter(){
        System.out.println("PrototypePrinter constructor...");
    }
    private int rand;

    @PostConstruct
    public void init(){
        rand = ThreadLocalRandom.current().nextInt(99, 999);
    }

    public void print(String str){
        System.out.println(rand + " => " + str);
    }
}
