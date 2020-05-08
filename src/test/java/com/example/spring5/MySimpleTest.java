package com.example.spring5;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class MySimpleTest {
//    @Configuration
//    @ComponentScan("com.example.logic.ann.props")
//    static class Config{}

    @Autowired
    private ApplicationContext context;

    @Test
    public void test(){
        System.out.println(context);
    }
}
