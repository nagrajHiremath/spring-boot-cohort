package com.spring.cohort.assignment.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class GlobalErrorAspect {

    @AfterThrowing(pointcut = "within(com.spring.cohort.assignment.annotations.ErrorHandler)", throwing = "ex")
    public void errorHandler(JoinPoint joinPoint, Exception ex){
        log.error("Error occurred in method: {} message: {}", joinPoint.getSignature().getName(), ex.getMessage());
    }

    @After("within(com.spring.cohort.assignment.service.DepartmentService)")
    public void afterHandler(JoinPoint joinPoint){
        log.debug("Method completed: {}", joinPoint.getSignature().getName());
    }
}
