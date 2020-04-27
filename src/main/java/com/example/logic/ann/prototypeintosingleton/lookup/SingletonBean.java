package com.example.logic.ann.prototypeintosingleton.lookup;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

@Component
public class SingletonBean {
    /**
     * Spring will use cglib to override this method
     * it will return => `return applicationContext.getBean(PrototypePrinter.class);`
     * Since cglib is used, this method can also be abstract
     */
    @Lookup
    public PrototypePrinter getPrinter(){
        return null;
    }


    public void sayHello(){
        getPrinter().print("I'm SingletonBean");
    }
}