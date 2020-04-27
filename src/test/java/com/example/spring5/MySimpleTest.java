package com.example.spring5;


import com.example.logic.ann.props.Person1;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.mock.env.MockPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//@ContextConfiguration
//@TestPropertySource("classpath:application.yml")
public class MySimpleTest extends AbstractJUnit4SpringContextTests {
//    @Configuration
//    @ComponentScan("com.example.logic.ann.props")
//    static class Config{}

    @Autowired
    private ApplicationContext context;

    @Test
    public void test(){
        System.out.println(context);
        System.out.println(applicationContext);
    }
}
