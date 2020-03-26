package com.example.logic.ann.prototypeintosingleton;

import javax.annotation.PostConstruct;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PrototypePrinter {
    private int rand;

    @PostConstruct
    public void init(){
        rand = ThreadLocalRandom.current().nextInt(1, 10);
    }

    public void print(String str){
        System.out.println(rand + " => " + str);
    }
}
