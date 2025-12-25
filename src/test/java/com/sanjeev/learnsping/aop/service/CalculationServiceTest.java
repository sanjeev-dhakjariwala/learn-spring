package com.sanjeev.learnsping.aop.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for CalculationService with AOP aspects.
 * Aspects will be triggered automatically for Spring-managed beans.
 */
@SpringBootTest
class CalculationServiceTest {

    @Autowired
    private CalculationService calculationService;

    @Test
    void contextLoads() {
        assertThat(calculationService).isNotNull();
    }

    @Test
    void add_shouldReturnCorrectSum() {
        // LoggingAspect will intercept this call
        int result = calculationService.add(5, 3);
        assertThat(result).isEqualTo(8);
    }

    @Test
    void addWithDelay_shouldReturnSumAndTrackTime() {
        // Both LoggingAspect and PerformanceAspect will intercept
        int result = calculationService.addWithDelay(10, 20);
        assertThat(result).isEqualTo(30);
    }

    @Test
    void multiply_shouldReturnProductAndBeAudited() {
        // LoggingAspect and AuditAspect will intercept
        int result = calculationService.multiply(6, 7);
        assertThat(result).isEqualTo(42);
    }

    @Test
    void divide_shouldThrowExceptionForZero() {
        // LoggingAspect @AfterThrowing will be triggered
        assertThatThrownBy(() -> calculationService.divide(10, 0))
                .isInstanceOf(ArithmeticException.class)
                .hasMessageContaining("Division by zero");
    }

    @Test
    void divide_shouldReturnQuotient() {
        int result = calculationService.divide(20, 4);
        assertThat(result).isEqualTo(5);
    }

    @Test
    void complexCalculation_shouldProcessAndTrack() {
        // Multiple aspects will intercept: Logging, Performance, Audit
        String result = calculationService.complexCalculation("hello");
        assertThat(result).isEqualTo("Processed: HELLO");
    }
}

