package com.example.spring.annotation.postprocessors;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ExceptionLoggerBPP implements BeanPostProcessor {
    private Map<String, List<String>> map = new HashMap<>();

    public Object postProcessBeforeInitialization(Object bean, String beanName) {

        for(var method: bean.getClass().getMethods()){
            if(method.getAnnotation(ExceptionLogger.class) != null){
                map.merge(beanName, new ArrayList<>(List.of(method.getName())), (l1, l2)->{
                    l1.addAll(l2);
                    return l1;
                });
            }
        }
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) {
        List<String> annotatedMethods = map.get(beanName);
        if(annotatedMethods != null){
            Class<?> cls = bean.getClass();
            return Proxy.newProxyInstance(cls.getClassLoader(), cls.getInterfaces(), ((proxy, m, args) -> {
                if(annotatedMethods.contains(m.getName())) {
                    System.out.println("__ExceptionLogger__");
                    Object retVal = m.invoke(bean, args);
                    System.out.println("__ExceptionLogger__");
                    return retVal;
                }
                return m.invoke(bean, args);
            }));
        }
        return bean;
    }
}
