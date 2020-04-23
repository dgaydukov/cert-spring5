package com.example.spring5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.logic.ann.props.Person1;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
//@TestPropertySource
public class JavaConfigTest {
    @Configuration
    @ComponentScan("com.example.logic.ann.props")
    static class Config{}

    @Autowired
    public ConfigurableApplicationContext context;

    @Test
    public void test(){
        System.out.println(context.getBean(Person1.class));
        TestPropertyValues.of("name=Jimmy").applyTo(context);
        System.out.println(context.getBean(Person1.class));
    }
}