package com.example.logic.ann;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class AwareBean implements BeanNameAware, ApplicationContextAware {
    private String beanName;
    private ApplicationContext context;

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public void setApplicationContext(ApplicationContext context) {
        this.context = context;
    }

    public void doWork(){
        System.out.println("beanName => " + beanName + ", context => " + context);
    }
}
