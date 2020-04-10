package com.example.logic.ann.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AopJavaConfig {
    @Bean
    public AopSimpleBean originalBean(){
        return new AopSimpleBean();
    }


    @Bean
    public ProxyFactoryBean aopBean(){
        ProxyFactoryBean pfb = new ProxyFactoryBean();
        pfb.setTarget(originalBean());
        pfb.setProxyTargetClass(true);
        pfb.addAdvice((MethodInterceptor) inv->{
            System.out.println("aroundAdvice => " + inv.getMethod().getName());
            Object retVal = inv.proceed();
            System.out.println("aroundAdvice => " + retVal);
            return retVal;
        });
        return pfb;
    }
}
