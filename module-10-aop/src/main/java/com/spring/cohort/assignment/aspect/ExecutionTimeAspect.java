package com.spring.cohort.assignment.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExecutionTimeAspect {

    @Around("@annotation(com.spring.cohort.assignment.annotations.TrackExecutionTime)")
    public Object trackExecutionTime(org.aspectj.lang.ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        // Execute the targeted method
        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        String methodName = joinPoint.getSignature().toShortString();

        // Creating a clear execution report inside logs
        log.debug("📊 --- PERFORMANCE REPORT ---");
        log.debug("Method executed: {}", methodName);
        log.debug("Time Taken: {} ms", executionTime);
        log.debug("-----------------------------");

        return result;
    }
}
