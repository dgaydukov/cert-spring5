package com.example.logic.ann.prototypeintosingleton.oldway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PsJavaConfig {
    @Bean
    public SingletonBean singletonBean(){
        return new SingletonBean() {
            @Override
            public PrototypePrinter getPrinter() {
                var printer = new PrototypePrinter();
                printer.init();
                return printer;
            }
        };
    }
}
