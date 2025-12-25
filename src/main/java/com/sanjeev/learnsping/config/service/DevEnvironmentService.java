package com.sanjeev.learnsping.config.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Development environment specific service.
 * Only active when 'dev' profile is enabled.
 */
@Service
@Profile("dev")
public class DevEnvironmentService implements EnvironmentService {

    @Override
    public String getEnvironmentName() {
        return "Development";
    }

    @Override
    public String getEnvironmentInfo() {
        return "Development environment - Use for local development and debugging";
    }

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public String getLogLevel() {
        return "DEBUG";
    }
}

