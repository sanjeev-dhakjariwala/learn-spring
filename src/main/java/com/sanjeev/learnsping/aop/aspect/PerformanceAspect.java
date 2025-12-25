package com.sanjeev.learnsping.aop.aspect;

import com.sanjeev.learnsping.aop.annotation.TrackExecutionTime;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Aspect for tracking method execution time.
 * Demonstrates @Around advice and custom annotation pointcut.
 */
@Aspect
@Component
@Order(2)
public class PerformanceAspect {
    private static final Logger log = LoggerFactory.getLogger(PerformanceAspect.class);

    /**
     * @Around advice for methods annotated with @TrackExecutionTime
     * ProceedingJoinPoint allows control over method execution
     */
    @Around("@annotation(trackExecutionTime)")
    public Object trackExecutionTime(ProceedingJoinPoint joinPoint, TrackExecutionTime trackExecutionTime) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        String description = trackExecutionTime.value().isEmpty() ? methodName : trackExecutionTime.value();

        log.info("[@Around - Before] Starting execution time tracking for: {}", description);

        long startTime = System.currentTimeMillis();
        Object result = null;
        Throwable thrownException = null;

        try {
            // Proceed with method execution
            result = joinPoint.proceed();
            return result;
        } catch (Throwable ex) {
            thrownException = ex;
            throw ex;
        } finally {
            long executionTime = System.currentTimeMillis() - startTime;

            if (thrownException != null) {
                log.warn("[@Around - After] Method {} failed after {} ms with exception: {}",
                        description, executionTime, thrownException.getMessage());
            } else {
                log.info("[@Around - After] Method {} executed in {} ms", description, executionTime);
            }
        }
    }

    /**
     * @Around advice for all controller methods to track API performance
     */
    @Around("within(com.sanjeev.learnsping.aop.controller..*)")
    public Object trackControllerPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        long startTime = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;
            log.info("[Controller Performance] {} executed in {} ms", methodName, executionTime);
            return result;
        } catch (Throwable ex) {
            long executionTime = System.currentTimeMillis() - startTime;
            log.error("[Controller Performance] {} failed after {} ms", methodName, executionTime);
            throw ex;
        }
    }
}

