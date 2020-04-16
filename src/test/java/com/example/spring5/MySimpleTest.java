package com.example.spring5;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.example.logic.ann.beans.JavaConfig;
import com.example.logic.ann.beans.Printer;

@SpringJUnitConfig(classes = JavaConfig.class)
public class MySimpleTest {
    @Autowired
    private Printer printer;

    @Test
    public void getVersion(){
        String currentVersion = Test.class.getPackage().getImplementationVersion();
        System.out.println("currentVersion => " + currentVersion);
    }

    @Test
    public void doWork(){
        printer.print("hello");
    }
}
