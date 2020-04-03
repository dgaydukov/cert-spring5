package com.example.logic.ann.prototypeintosingleton.oldwaay;

public abstract class SingletonBean {
    public abstract PrototypePrinter getPrinter();

    public void sayHello(){
        getPrinter().print("I'm SingletonBean");
    }
}
