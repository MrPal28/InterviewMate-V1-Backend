package com.interviewmate.userservice.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before("execution(* com.interviewmate.userservice.service..*(..)) || execution(* com.interviewmate.userservice.eventproducerandconsumer..*(..))")
    public void logMethodEntry(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = Arrays.stream(joinPoint.getArgs())
                .map(this::maskSensitive)
                .toArray();
        log.info("Entering method: {} with arguments: {}", methodName, Arrays.toString(args));
    }

    @AfterReturning(value = "execution(* com.interviewmate.userservice.service..*(..)) || execution(* com.interviewmate.userservice.eventproducerandconsumer..*(..))", returning = "result")
    public void logMethodExit(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().toShortString();
        log.info("Exiting method: {} with result: {}", methodName, maskSensitive(result));
    }

    @AfterThrowing(value = "execution(* com.interviewmate.userservice.service..*(..)) || execution(* com.interviewmate.userservice.eventproducerandconsumer..*(..))", throwing = "ex")
    public void logMethodException(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().toShortString();
        log.error("Exception in method: {} with message: {}", methodName, ex.getMessage(), ex);
    }

    private Object maskSensitive(Object arg) {
        if (arg == null) return null;
        if (arg instanceof String && ((String) arg).contains("@")) return "*****@*****";
        if (arg instanceof String && ((String) arg).matches("\\d{6}")) return "******";
        if (arg instanceof String && ((String) arg).toLowerCase().contains("password")) return "********";
        return arg;
    }
}
