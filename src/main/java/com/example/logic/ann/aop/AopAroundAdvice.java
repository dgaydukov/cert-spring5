package com.example.logic.ann.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class AopAroundAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation inv) throws Throwable {
        System.out.println("aroundAdvice => " + inv.getMethod().getName());
        Object retVal = inv.proceed();
        System.out.println("aroundAdvice => " + retVal);
        return retVal;
    }
}
