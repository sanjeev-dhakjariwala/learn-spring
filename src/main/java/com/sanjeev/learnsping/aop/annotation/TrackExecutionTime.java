package com.sanjeev.learnsping.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation to track execution time of methods.
 * Methods annotated with @TrackExecutionTime will have their execution time logged.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TrackExecutionTime {
    /**
     * Optional description of what's being tracked
     */
    String value() default "";
}

