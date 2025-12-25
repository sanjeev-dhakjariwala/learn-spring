package com.sanjeev.learnspring.config.properties;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * Type-safe configuration properties for database settings.
 * Demonstrates @ConfigurationProperties with validation.
 */
@Component
@ConfigurationProperties(prefix = "app.database")
@Validated
public class DatabaseProperties {

    @NotBlank(message = "Database URL must not be blank")
    private String url;

    @NotBlank(message = "Database username must not be blank")
    private String username;

    private String password;

    private PoolProperties pool = new PoolProperties();

    // Getters and Setters
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PoolProperties getPool() {
        return pool;
    }

    public void setPool(PoolProperties pool) {
        this.pool = pool;
    }

    /**
     * Nested configuration for connection pool settings.
     */
    public static class PoolProperties {
        @Min(value = 1, message = "Pool min size must be at least 1")
        private int minSize = 5;

        @Max(value = 100, message = "Pool max size must not exceed 100")
        private int maxSize = 20;

        private long timeout = 30000;

        public int getMinSize() {
            return minSize;
        }

        public void setMinSize(int minSize) {
            this.minSize = minSize;
        }

        public int getMaxSize() {
            return maxSize;
        }

        public void setMaxSize(int maxSize) {
            this.maxSize = maxSize;
        }

        public long getTimeout() {
            return timeout;
        }

        public void setTimeout(long timeout) {
            this.timeout = timeout;
        }
    }
}

