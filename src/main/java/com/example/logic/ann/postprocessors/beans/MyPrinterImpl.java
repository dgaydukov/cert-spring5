package com.example.logic.ann.postprocessors.beans;

import com.example.logic.ann.postprocessors.annotation.LoggingWrapper;
import com.example.logic.ann.postprocessors.annotation.TransactionWrapper;
import org.springframework.stereotype.Component;

@Component
class MyPrinterImpl implements MyPrinter{
    @LoggingWrapper
    @TransactionWrapper
    public void print(){
        System.out.println("printing...");
    }
}
