package com.sanjeev.learnspring.config.controller;

import com.sanjeev.learnspring.config.service.AppInfoService;
import com.sanjeev.learnspring.config.service.ConfigurationService;
import com.sanjeev.learnspring.config.service.EnvironmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * REST controller demonstrating configuration and property management.
 */
@RestController
@RequestMapping("/api/config")
public class ConfigurationController {

    private final AppInfoService appInfoService;
    private final ConfigurationService configurationService;
    private final EnvironmentService environmentService;
    private final Environment environment;

    @Autowired
    public ConfigurationController(AppInfoService appInfoService,
                                    ConfigurationService configurationService,
                                    EnvironmentService environmentService,
                                    Environment environment) {
        this.appInfoService = appInfoService;
        this.configurationService = configurationService;
        this.environmentService = environmentService;
        this.environment = environment;
    }

    /**
     * Get application info from @Value properties.
     */
    @GetMapping("/app-info")
    public Map<String, Object> getAppInfo() {
        Map<String, Object> response = new HashMap<>();
        response.put("name", appInfoService.getAppName());
        response.put("version", appInfoService.getAppVersion());
        response.put("description", appInfoService.getAppDescription());
        response.put("environment", appInfoService.getEnvironment());
        response.put("systemUser", appInfoService.getSystemUser());
        response.put("fullInfo", appInfoService.getFullInfo());
        response.put("features", Map.of(
                "emailEnabled", appInfoService.isEmailEnabled(),
                "smsEnabled", appInfoService.isSmsEnabled(),
                "maxUploadSize", appInfoService.getMaxUploadSize()
        ));
        return response;
    }

    /**
     * Get database configuration from @ConfigurationProperties.
     */
    @GetMapping("/database")
    public Map<String, Object> getDatabaseConfig() {
        var dbProps = configurationService.getDatabaseProperties();
        Map<String, Object> response = new HashMap<>();
        response.put("url", dbProps.getUrl());
        response.put("username", dbProps.getUsername());
        response.put("poolMinSize", dbProps.getPool().getMinSize());
        response.put("poolMaxSize", dbProps.getPool().getMaxSize());
        response.put("poolTimeout", dbProps.getPool().getTimeout());
        response.put("info", configurationService.getDatabaseInfo());
        return response;
    }

    /**
     * Get API configuration from @ConfigurationProperties.
     */
    @GetMapping("/api")
    public Map<String, Object> getApiConfig() {
        var apiProps = configurationService.getApiProperties();
        Map<String, Object> response = new HashMap<>();
        response.put("baseUrl", apiProps.getBaseUrl());
        response.put("timeout", apiProps.getTimeout());
        response.put("retryCount", apiProps.getRetryCount());
        response.put("keyConfigured", apiProps.getKey() != null && !apiProps.getKey().isEmpty());
        response.put("info", configurationService.getApiInfo());
        return response;
    }

    /**
     * Get environment-specific service info (demonstrates @Profile).
     */
    @GetMapping("/environment")
    public Map<String, Object> getEnvironmentInfo() {
        Map<String, Object> response = new HashMap<>();
        response.put("name", environmentService.getEnvironmentName());
        response.put("info", environmentService.getEnvironmentInfo());
        response.put("debugEnabled", environmentService.isDebugEnabled());
        response.put("logLevel", environmentService.getLogLevel());
        response.put("activeProfiles", Arrays.asList(environment.getActiveProfiles()));
        response.put("defaultProfiles", Arrays.asList(environment.getDefaultProfiles()));
        return response;
    }

    /**
     * Get all configuration in one endpoint.
     */
    @GetMapping("/all")
    public Map<String, Object> getAllConfig() {
        Map<String, Object> response = new HashMap<>();
        response.put("appInfo", getAppInfo());
        response.put("database", getDatabaseConfig());
        response.put("api", getApiConfig());
        response.put("environment", getEnvironmentInfo());
        return response;
    }

    /**
     * Demonstrates direct Environment access to get any property.
     */
    @GetMapping("/property")
    public Map<String, Object> getSpecificProperty() {
        Map<String, Object> response = new HashMap<>();
        response.put("appName", environment.getProperty("app.name"));
        response.put("appVersion", environment.getProperty("app.version"));
        response.put("notificationEnabled", environment.getProperty("app.notification.enabled", Boolean.class));
        response.put("notificationEmail", environment.getProperty("app.notification.email"));
        response.put("propertyWithDefault", environment.getProperty("non.existent.property", "DEFAULT_VALUE"));
        return response;
    }
}

