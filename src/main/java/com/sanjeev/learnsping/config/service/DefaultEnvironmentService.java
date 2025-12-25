package com.sanjeev.learnsping.config.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

/**
 * Default environment service when no profile is active.
 * Uses @ConditionalOnMissingBean to be created only if no other EnvironmentService exists.
 */
@Service
@ConditionalOnMissingBean(EnvironmentService.class)
public class DefaultEnvironmentService implements EnvironmentService {

    @Override
    public String getEnvironmentName() {
        return "Default";
    }

    @Override
    public String getEnvironmentInfo() {
        return "Default environment - No specific profile active";
    }

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public String getLogLevel() {
        return "INFO";
    }
}

