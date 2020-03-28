package com.example.logic.ann.collections;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class User3 implements User {
    @Override
    public String toString(){
        return "user3";
    }
}
