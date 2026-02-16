package com.interviewmate.userservice.aspect;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* com.interviewmate.userservice.service..*(..)) || " +
              "execution(* com.interviewmate.userservice.eventproducerandconsumer..*(..))")
    public void applicationLayer() {}

    @Around("applicationLayer()")
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {

        String methodName = joinPoint.getSignature().toShortString();
        long start = System.currentTimeMillis();

        if (log.isInfoEnabled()) {
            Object[] args = Arrays.stream(joinPoint.getArgs())
                    .map(this::maskSensitive)
                    .toArray();
            log.info("Entering method: {} with arguments: {}", methodName, Arrays.toString(args));
        }

        try {
            Object result = joinPoint.proceed();

            long executionTime = System.currentTimeMillis() - start;

            if (log.isDebugEnabled()) {
                log.debug("Method {} returned: {}", methodName, maskSensitive(result));
            }

            log.info("Exiting method: {} | Execution time: {} ms", methodName, executionTime);

            return result;

        } catch (Throwable ex) {

            long executionTime = System.currentTimeMillis() - start;

            log.error("Exception in method: {} | Execution time: {} ms | Message: {}",
                    methodName,
                    executionTime,
                    ex.getMessage(),
                    ex);

            throw ex;
        }
    }

    private Object maskSensitive(Object arg) {
        if (arg == null) return null;

        if (arg instanceof String str) {
            if (str.contains("@")) return "*****@*****";
            if (str.matches("\\d{6}")) return "******";
            if (str.toLowerCase().contains("password")) return "********";
        }

        return arg;
    }
}
