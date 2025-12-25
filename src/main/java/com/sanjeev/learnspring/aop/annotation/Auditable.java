package com.sanjeev.learnspring.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation to mark methods that should be audited.
 * Audit logs will capture method name, arguments, and results.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auditable {
    /**
     * Audit action name
     */
    String action() default "";

    /**
     * Whether to log method arguments
     */
    boolean logArgs() default true;

    /**
     * Whether to log return value
     */
    boolean logResult() default true;
}

