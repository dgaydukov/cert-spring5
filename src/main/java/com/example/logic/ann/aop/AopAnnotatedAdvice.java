package com.example.logic.ann.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
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

    @After("isOriginalBean() && isSayHelloMethod()")
    public void afterAdvice(JoinPoint jp){
        System.out.println("afterAdvice => " + jp.getSignature().getName());
    }

    @Around("isOriginalBean() && isSayHelloMethod()")
    public void aroundAdvice(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("aroundAdvice => " + jp.getSignature().getName());
        Object retVal = jp.proceed();
        System.out.println("aroundAdvice => " + jp.getSignature().getName() + ", retVal => " + retVal);
    }

    @AfterReturning(pointcut = "isOriginalBean() && isSayHelloMethod()", returning="retVal")
    public void afterReturnAdvice(JoinPoint jp, Object retVal){
        System.out.println("afterReturnAdvice => " + jp.getSignature().getName() + ", retVal => " + retVal);
    }

    @AfterReturning(value = "execution(* getName(..))", returning="retVal")
    public void afterReturnAdviceException(JoinPoint jp, Object retVal){
        System.out.println("afterReturnAdvice => " + jp.getSignature().getName() + ", retVal => " + retVal);
        throw new RuntimeException("oops");
    }
}
