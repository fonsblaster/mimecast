package com.mimecast.eu.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

@Aspect
@Log4j2
@Configuration
public class LogEverythingAspect {
    @Around("com.mimecast.eu.aspect.JoinPointConfiguration.logEverythingAnnotation()")
    public Object calculateMethodExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("Method {} started execution", proceedingJoinPoint);
        log.info("Method {} arguments are {}", proceedingJoinPoint, proceedingJoinPoint.getArgs());
        Object retVal = proceedingJoinPoint.proceed();
        log.info("Method {} completed execution ", proceedingJoinPoint);
        return retVal;
    }
}