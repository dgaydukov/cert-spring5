package com.example.logic.ann.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AopAnnotatedAdvice {
    @Pointcut("bean(original*)")
    public void isOriginalBean(){}

    @Pointcut("execution(* sayHello(..))")
    public void isSayHelloMethod(){}

    @Before("isOriginalBean() && isSayHelloMethod()")
    public void beforeAdvice(JoinPoint jp){
        System.out.println("beforeAdvice => " + jp.getSignature().getName());
    }
}
