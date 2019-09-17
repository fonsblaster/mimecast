package com.mimecast.eu.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class JoinPointConfiguration {
    @Pointcut("@annotation(com.mimecast.eu.aspect.LogEverything)")
    public void logEverythingAnnotation() {}

    //    @Pointcut("execution(* com.mastering.spring.ch03aopwithspring.OrderDao.*(..))")
    //    public void orderDaoExecution() {}
}