package com.sanjeev.learnspring.aop.aspect;

import com.sanjeev.learnspring.aop.annotation.Auditable;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Aspect for auditing method executions.
 * Demonstrates custom annotation with parameters and audit logging.
 */
@Aspect
@Component
@Order(3)
public class AuditAspect {
    private static final Logger log = LoggerFactory.getLogger(AuditAspect.class);

    @Around("@annotation(auditable)")
    public Object auditMethod(ProceedingJoinPoint joinPoint, Auditable auditable) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        String action = auditable.action().isEmpty() ? methodName : auditable.action();

        // Audit log before execution
        StringBuilder auditLog = new StringBuilder();
        auditLog.append(String.format("[AUDIT] Action: %s | Time: %s | Method: %s",
                action, LocalDateTime.now(), methodName));

        if (auditable.logArgs() && joinPoint.getArgs().length > 0) {
            auditLog.append(String.format(" | Arguments: %s", Arrays.toString(joinPoint.getArgs())));
        }

        log.info(auditLog.toString());

        Object result = null;
        try {
            result = joinPoint.proceed();

            // Audit log after successful execution
            if (auditable.logResult() && result != null) {
                log.info("[AUDIT] Action: {} | Status: SUCCESS | Result: {}", action, result);
            } else {
                log.info("[AUDIT] Action: {} | Status: SUCCESS", action);
            }

            return result;
        } catch (Throwable ex) {
            log.error("[AUDIT] Action: {} | Status: FAILURE | Error: {}", action, ex.getMessage());
            throw ex;
        }
    }
}

