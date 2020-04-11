package com.example.spring5;

import com.example.logic.ann.beans.SimpleBean;
import com.example.logic.ann.beans.SimplePrinter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class BeansIntegrationTest {
    /**
     * In case you want to scan package you should add
     * inner static class
     */
    @Configuration
    @ComponentScan("com.example.logic.ann.beans")
    public static class TestConfig{}

    @Mock
    private SimplePrinter simplePrinter;

    @InjectMocks
    private SimpleBean simpleBean;

    @Test
    public void testSayHello(){
        doAnswer(inv->{
            System.out.println("mock => " + inv.getArgument(0).toString());
            return null;
        }).when(simplePrinter).print(any(String.class));
        simpleBean.sayHello();
    }
}