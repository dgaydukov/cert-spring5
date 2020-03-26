package com.example.logic.ann.prototypeintosingleton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingletonBean {
    @Autowired
    private PrototypePrinter printer;


    public void sayHello(){
        printer.print("I'm SingletonBean");
    }
}
