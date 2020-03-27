package com.example.logic.ann.postprocessors;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class MyBFPP implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
        System.out.println("__MyBFPP");
        for(String name: factory.getBeanDefinitionNames()){
            // change primary
            // set init method
            // change bean completely
            // dynamically add new bean http://dimafeng.com/2015/11/27/dynamic-bean-definition/
            if("simpleBean".equals(name)){
                BeanDefinition beanDefinition = factory.getBeanDefinition(name);
                beanDefinition.setInitMethodName("init2");
            }
        }
    }
}
