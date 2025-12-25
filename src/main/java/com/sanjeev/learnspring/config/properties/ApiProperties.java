package com.sanjeev.learnspring.config.properties;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * Type-safe configuration properties for API settings.
 */
@Component
@ConfigurationProperties(prefix = "app.api")
@Validated
public class ApiProperties {

    @NotBlank(message = "API base URL must not be blank")
    private String baseUrl;

    @Min(value = 1000, message = "Timeout must be at least 1000ms")
    private int timeout = 5000;

    @Min(value = 0, message = "Retry count cannot be negative")
    private int retryCount = 3;

    private String key;

    // Getters and Setters
    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

