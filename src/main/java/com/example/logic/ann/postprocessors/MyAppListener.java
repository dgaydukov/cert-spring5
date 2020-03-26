package com.example.logic.ann.postprocessors;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.example.logic.ann.postprocessors.annotation.PostAppReady;

@Component
public class MyAppListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        for(String name: context.getBeanDefinitionNames()){
            Object bean = context.getBean(name);
            for(Method method: bean.getClass().getMethods()) {
                Annotation ann = method.getAnnotation(PostAppReady.class);
                if(ann != null){
                    try {
                        method.invoke(bean, method.getParameters());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
