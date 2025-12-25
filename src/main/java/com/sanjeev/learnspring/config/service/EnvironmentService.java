package com.sanjeev.learnspring.config.service;

/**
 * Interface for environment-specific services.
 * Demonstrates @Profile usage with different implementations.
 */
public interface EnvironmentService {
    String getEnvironmentName();
    String getEnvironmentInfo();
    boolean isDebugEnabled();
    String getLogLevel();
}

