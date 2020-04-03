package com.example.logic.ann.beans;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class JavaConfig {
    @Bean
    public MessageSource messageSource(){
        ResourceBundleMessageSource bundle = new ResourceBundleMessageSource();
        bundle.setBasename("i18n/msg");
        return bundle;
    }

    @Bean
    public Printer printer(){
        return new SimplePrinter();
    }

    @Bean
    public SimpleBean simpleBean(){
        return new SimpleBean();
    }
}
