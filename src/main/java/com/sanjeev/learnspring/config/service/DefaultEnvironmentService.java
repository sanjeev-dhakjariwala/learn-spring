package com.sanjeev.learnspring.config.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Default environment service when no profile is active.
 * Active when neither 'dev' nor 'prod' profiles are set.
 */
@Service
@Profile("default")
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

