package com.sanjeev.learnsping.aop.service;

import com.sanjeev.learnsping.aop.annotation.Auditable;
import com.sanjeev.learnsping.aop.annotation.TrackExecutionTime;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Service to demonstrate AOP aspects in action.
 * All public methods will be intercepted by LoggingAspect.
 */
@Service
public class CalculationService {

    private final Random random = new Random();

    /**
     * Simple calculation - intercepted by LoggingAspect
     */
    public int add(int a, int b) {
        return a + b;
    }

    /**
     * Method with @TrackExecutionTime - also tracked by PerformanceAspect
     */
    @TrackExecutionTime("Addition with delay")
    public int addWithDelay(int a, int b) {
        simulateDelay(100);
        return a + b;
    }

    /**
     * Method with @Auditable annotation
     */
    @Auditable(action = "MULTIPLY_NUMBERS", logArgs = true, logResult = true)
    public int multiply(int a, int b) {
        return a * b;
    }

    /**
     * Method that throws exception - triggers @AfterThrowing
     */
    public int divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return a / b;
    }

    /**
     * Complex method with multiple annotations
     */
    @TrackExecutionTime("Complex calculation")
    @Auditable(action = "COMPLEX_CALCULATION", logArgs = true, logResult = true)
    public String complexCalculation(String input) {
        simulateDelay(200);
        return "Processed: " + input.toUpperCase();
    }

    private void simulateDelay(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

