package com.sanjeev.learnspring.config.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service demonstrating @Value annotation for simple property injection.
 */
@Service
public class AppInfoService {

    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @Value("${app.description}")
    private String appDescription;

    // @Value with default value
    @Value("${app.environment:UNKNOWN}")
    private String environment;

    // @Value with SpEL (Spring Expression Language)
    @Value("#{systemProperties['user.name']}")
    private String systemUser;

    @Value("${app.features.email-enabled}")
    private boolean emailEnabled;

    @Value("${app.features.sms-enabled}")
    private boolean smsEnabled;

    @Value("${app.features.max-upload-size}")
    private long maxUploadSize;

    public String getAppName() {
        return appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public String getAppDescription() {
        return appDescription;
    }

    public String getEnvironment() {
        return environment;
    }

    public String getSystemUser() {
        return systemUser;
    }

    public boolean isEmailEnabled() {
        return emailEnabled;
    }

    public boolean isSmsEnabled() {
        return smsEnabled;
    }

    public long getMaxUploadSize() {
        return maxUploadSize;
    }

    public String getFullInfo() {
        return String.format("%s v%s - %s (Environment: %s, User: %s)",
                appName, appVersion, appDescription, environment, systemUser);
    }
}

