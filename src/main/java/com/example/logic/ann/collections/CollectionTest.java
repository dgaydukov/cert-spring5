package com.example.logic.ann.collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class CollectionTest {
    @Autowired
    //@Qualifier("users")
    private List<User> user;

    @PostConstruct
    private void init(){
        System.out.println(user);
    }
}
