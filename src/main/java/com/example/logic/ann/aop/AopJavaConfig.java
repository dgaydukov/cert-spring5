package com.example.logic.ann.aop;

import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AopJavaConfig {
    @Bean
    public AopSimpleBean originalAopSimpleBean(){
        return new AopSimpleBean();
    }

    @Bean
    public AopAroundAdvice aopBeforeAdvice(){
        return new AopAroundAdvice();
    }

    @Bean
    public ProxyFactoryBean aopSimpleBean(){
        ProxyFactoryBean pfb = new ProxyFactoryBean();
        pfb.setTarget(originalAopSimpleBean());
        pfb.setProxyTargetClass(true);
        pfb.addAdvice(aopBeforeAdvice());
        return pfb;
    }
}
