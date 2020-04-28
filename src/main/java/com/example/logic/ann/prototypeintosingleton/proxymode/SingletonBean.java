package com.example.logic.ann.prototypeintosingleton.proxymode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SingletonBean {
    @Autowired
    private PrototypePrinter printer;

    public void sayHello(){
        printer.print("I'm SingletonBean");
    }
}