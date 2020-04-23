package com.example.spring5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.logic.ann.beans.SimpleBean;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
public class JavaConfigTest {
    @Configuration
    @ComponentScan("com.example.logic.ann.beans")
    private static class Config{}

    @Autowired
    public ApplicationContext context;

    @Test
    public void test(){
        System.out.println(context.getClass());
        System.out.println(context.getBean(SimpleBean.class));
    }
}
