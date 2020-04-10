package com.example.logic.ann.postprocessors;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * Use-cases:
 * 1. change primary
 * 2. set init method
 * 3. change bean completely
 * 4. dynamically add new bean http://dimafeng.com/2015/11/27/dynamic-bean-definition/
 */
@Component
public class MyBFPP implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) {
        System.out.println("__MyBFPP");
        for(String beanName: factory.getBeanDefinitionNames()){
            BeanDefinition beanDefinition = factory.getBeanDefinition(beanName);
            String className = beanDefinition.getBeanClassName();
            System.out.println(beanName + " => " + className);
        }
    }
}
