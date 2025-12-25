package com.sanjeev.learnspring.lifecycle.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class demonstrating lifecycle callbacks.
 * This shows that @PostConstruct/@PreDestroy work on @Configuration classes too.
 */
@Configuration
public class LifecycleConfig {
    private static final Logger log = LoggerFactory.getLogger(LifecycleConfig.class);

    public LifecycleConfig() {
        log.info("=== LifecycleConfig constructor called ===");
    }

    @PostConstruct
    public void init() {
        log.info("=== LifecycleConfig @PostConstruct - Application context is initializing ===");
        log.info("=== All beans are being created and configured ===");
    }

    @PreDestroy
    public void destroy() {
        log.info("=== LifecycleConfig @PreDestroy - Application context is shutting down ===");
        log.info("=== Cleaning up resources ===");
    }
}

