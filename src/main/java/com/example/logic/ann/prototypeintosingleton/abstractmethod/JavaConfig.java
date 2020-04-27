package com.example.logic.ann.prototypeintosingleton.abstractmethod;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class JavaConfig {
    @Bean
    public SingletonBean singletonBean(){
        return new SingletonBean() {
            @Override
            public PrototypePrinter getPrinter() {
                return prototypePrinter();
            }
        };
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public PrototypePrinter prototypePrinter(){
        return new PrototypePrinter();
    }


}
