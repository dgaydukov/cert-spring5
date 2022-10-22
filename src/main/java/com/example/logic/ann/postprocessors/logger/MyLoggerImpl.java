package com.example.logic.ann.postprocessors.logger;

import com.example.logic.ann.postprocessors.annotation.LoggingWrapper;
import org.springframework.stereotype.Component;

@Component
public class MyLoggerImpl implements MyLogger {
    @Override
    @LoggingWrapper
    public void m1() {
        System.out.println("m1");
        m2();
    }

    @Override
    @LoggingWrapper
    public void m2() {
        System.out.println("m2");
    }
}
