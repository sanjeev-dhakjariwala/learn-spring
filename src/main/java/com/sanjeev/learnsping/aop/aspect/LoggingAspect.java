package com.sanjeev.learnsping.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Aspect for logging method executions.
 * Demonstrates @Before, @AfterReturning, @AfterThrowing, and @After advice.
 */
@Aspect
@Component
@Order(1)
public class LoggingAspect {
    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Pointcut for all methods in service package
     */
    @Pointcut("within(com.sanjeev.learnsping.aop.service..*)")
    public void serviceLayer() {}

    /**
     * Pointcut for all public methods
     */
    @Pointcut("execution(public * *(..))")
    public void publicMethod() {}

    /**
     * @Before advice - runs before method execution
     */
    @Before("serviceLayer() && publicMethod()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("[@Before] Entering method: {} with arguments: {}",
                joinPoint.getSignature().toShortString(),
                Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * @AfterReturning advice - runs after successful method execution
     */
    @AfterReturning(pointcut = "serviceLayer() && publicMethod()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("[@AfterReturning] Method: {} returned: {}",
                joinPoint.getSignature().toShortString(),
                result);
    }

    /**
     * @AfterThrowing advice - runs when method throws exception
     */
    @AfterThrowing(pointcut = "serviceLayer() && publicMethod()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        log.error("[@AfterThrowing] Method: {} threw exception: {}",
                joinPoint.getSignature().toShortString(),
                exception.getMessage());
    }

    /**
     * @After advice - runs after method execution (finally block)
     */
    @After("serviceLayer() && publicMethod()")
    public void logAfter(JoinPoint joinPoint) {
        log.info("[@After] Completed method: {}",
                joinPoint.getSignature().toShortString());
    }
}

