package com.example.logic.ann.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DiJavaConfig {
    @Bean
    public MyStaticBean myStaticBean(){
        return MyStaticBean.getInstance();
    }
}
