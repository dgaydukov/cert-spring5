package com.example.logic.ann.postprocessors.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBeanJavaConfig {
    @Bean
    public MyPrinter mySecondPrinter(){
        return new MyPrinterImpl();
    }
}
