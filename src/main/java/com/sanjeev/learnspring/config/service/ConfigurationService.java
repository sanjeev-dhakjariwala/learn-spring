package com.sanjeev.learnspring.config.service;

import com.sanjeev.learnspring.config.properties.ApiProperties;
import com.sanjeev.learnspring.config.properties.DatabaseProperties;
import org.springframework.stereotype.Service;

/**
 * Service demonstrating @ConfigurationProperties usage.
 * Shows type-safe configuration with nested properties.
 */
@Service
public class ConfigurationService {

    private final DatabaseProperties databaseProperties;
    private final ApiProperties apiProperties;

    public ConfigurationService(DatabaseProperties databaseProperties,
                                ApiProperties apiProperties) {
        this.databaseProperties = databaseProperties;
        this.apiProperties = apiProperties;
    }

    public String getDatabaseInfo() {
        return String.format("Database: %s (User: %s, Pool: %d-%d, Timeout: %dms)",
                databaseProperties.getUrl(),
                databaseProperties.getUsername(),
                databaseProperties.getPool().getMinSize(),
                databaseProperties.getPool().getMaxSize(),
                databaseProperties.getPool().getTimeout());
    }

    public String getApiInfo() {
        return String.format("API: %s (Timeout: %dms, Retries: %d)",
                apiProperties.getBaseUrl(),
                apiProperties.getTimeout(),
                apiProperties.getRetryCount());
    }

    public DatabaseProperties getDatabaseProperties() {
        return databaseProperties;
    }

    public ApiProperties getApiProperties() {
        return apiProperties;
    }
}

