package com.example.spring5;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration//("classpath:JavaConfigTest-content.xml")
public class JavaConfigTest {
    @Autowired
    public ApplicationContext context;

    @Test
    public void test(){
        System.out.println(context.getBean(String.class).getClass());
    }
}
