package com.example.spring5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.logic.ann.props.Person1;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = {JavaConfigTest.PropsACI.class}, classes = {JavaConfigTest.Config.class})
@TestPropertySource
public class JavaConfigTest {
    @ComponentScan("com.example.logic.ann.props")
    static class Config{}

    static class PropsACI implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext context) {
            TestPropertyValues.of("age=22").applyTo(context);
        }
    }

    @Autowired
    private ConfigurableApplicationContext context;

    @Test
    public void test(){
        System.out.println(context.getBean(Person1.class));
    }
}