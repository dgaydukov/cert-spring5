package com.example.logic.ann.collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CollectionsJavaConfig {
    @Bean
    public List<User> users(){
        return List.of(new User() {
            @Override
            public String toString(){
                return "collection user";
            }
        });
    }
}
