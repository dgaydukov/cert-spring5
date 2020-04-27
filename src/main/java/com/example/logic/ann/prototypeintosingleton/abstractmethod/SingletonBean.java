package com.example.logic.ann.prototypeintosingleton.abstractmethod;

public abstract class SingletonBean {
    public abstract PrototypePrinter getPrinter();

    public void sayHello(){
        getPrinter().print("I'm SingletonBean");
    }
}
