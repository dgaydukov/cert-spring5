package com.example.logic.ann.prototypeintosingleton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;

@Service
public class SingletonBean {
    @Autowired
    private PrototypePrinter printer;

    /**
     * If we remove proxyMode = ScopedProxyMode.TARGET_CLASS from PrototypePrinter
     * @Autowired would inject the same instance of printer here, but we can use this method
     * with @Lookup annotation to get new instance every time (we can return null, cause anyway Lookup will overwrite return value)
     */
    @Lookup
    public PrototypePrinter getPrinter(){
        return null;
    }


    public void sayHello(){
        printer.print("I'm SingletonBean");
    }
}
