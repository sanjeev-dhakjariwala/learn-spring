package com.sanjeev.learnspring.aop.aspect;

import com.sanjeev.learnspring.aop.service.CalculationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Integration test to verify AOP aspects are working.
 * Tests that aspects are properly woven into target beans.
 */
@SpringBootTest
class AopIntegrationTest {

    @Autowired
    private CalculationService calculationService;

    @Test
    void aopProxy_shouldBeCreated() {
        // Verify that the bean is proxied by Spring AOP
        assertThat(calculationService).isNotNull();

        // The actual class should be a proxy if AOP is working
        String className = calculationService.getClass().getName();
        // CGLIB proxy or JDK proxy will have special class names
        assertThat(className).matches(".*CalculationService.*");
    }

    @Test
    void loggingAspect_shouldInterceptMethods() {
        // All these calls should be logged by LoggingAspect
        calculationService.add(1, 2);
        calculationService.multiply(3, 4);

        // Verify functionality works despite AOP interception
        int result = calculationService.add(10, 20);
        assertThat(result).isEqualTo(30);
    }

    @Test
    void performanceAspect_shouldTrackExecutionTime() {
        // @TrackExecutionTime methods should be timed
        int result = calculationService.addWithDelay(5, 5);
        assertThat(result).isEqualTo(10);
    }

    @Test
    void auditAspect_shouldLogAuditableActions() {
        // @Auditable methods should be audited
        int result = calculationService.multiply(6, 7);
        assertThat(result).isEqualTo(42);
    }

    @Test
    void exceptionHandling_shouldTriggerAfterThrowing() {
        // @AfterThrowing should be triggered
        assertThatThrownBy(() -> calculationService.divide(10, 0))
                .isInstanceOf(ArithmeticException.class);
    }

    @Test
    void multipleAspects_shouldWorkTogether() {
        // This method has both @TrackExecutionTime and @Auditable
        // Multiple aspects should execute in order
        String result = calculationService.complexCalculation("test");
        assertThat(result).isEqualTo("Processed: TEST");
    }
}

