package com.example.logic.ann.collections;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class User2 implements User {
    @Override
    public String toString(){
        return "user2";
    }
}
