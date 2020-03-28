package com.example.logic.ann.collections;

import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class User1 implements User {
    @Override
    public String toString(){
        return "user1";
    }
}
