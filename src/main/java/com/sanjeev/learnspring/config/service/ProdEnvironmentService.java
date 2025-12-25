package com.sanjeev.learnspring.config.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Production environment specific service.
 * Only active when 'prod' profile is enabled.
 */
@Service
@Profile("prod")
public class ProdEnvironmentService implements EnvironmentService {

    @Override
    public String getEnvironmentName() {
        return "Production";
    }

    @Override
    public String getEnvironmentInfo() {
        return "Production environment - Optimized for performance and stability";
    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public String getLogLevel() {
        return "INFO";
    }
}

