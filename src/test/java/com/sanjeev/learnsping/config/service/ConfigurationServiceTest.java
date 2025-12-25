package com.sanjeev.learnsping.config.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ConfigurationServiceTest {

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private ConfigurationService configurationService;

    @Test
    void contextLoads() {
        assertThat(appInfoService).isNotNull();
        assertThat(configurationService).isNotNull();
    }

    @Test
    void appInfoService_loadsValuesFromProperties() {
        assertThat(appInfoService.getAppName()).isNotBlank();
        assertThat(appInfoService.getAppVersion()).isNotBlank();
        assertThat(appInfoService.getAppDescription()).isNotBlank();
    }

    @Test
    void appInfoService_hasDefaultValue() {
        // Environment property has default value "UNKNOWN" if not set
        assertThat(appInfoService.getEnvironment()).isNotNull();
    }

    @Test
    void appInfoService_spelExpressionWorks() {
        // SpEL expression gets system property
        assertThat(appInfoService.getSystemUser()).isNotBlank();
    }

    @Test
    void appInfoService_booleanValuesWork() {
        assertThat(appInfoService.isEmailEnabled()).isTrue();
        assertThat(appInfoService.isSmsEnabled()).isFalse();
    }

    @Test
    void appInfoService_numericValuesWork() {
        assertThat(appInfoService.getMaxUploadSize()).isPositive();
        assertThat(appInfoService.getMaxUploadSize()).isEqualTo(10485760L);
    }

    @Test
    void configurationService_databasePropertiesLoaded() {
        var dbProps = configurationService.getDatabaseProperties();

        assertThat(dbProps.getUrl()).isNotBlank();
        assertThat(dbProps.getUsername()).isNotBlank();
        assertThat(dbProps.getPool().getMinSize()).isPositive();
        assertThat(dbProps.getPool().getMaxSize()).isPositive();
        assertThat(dbProps.getPool().getTimeout()).isPositive();
    }

    @Test
    void configurationService_nestedPropertiesWork() {
        var dbProps = configurationService.getDatabaseProperties();

        // Nested pool properties should be loaded
        assertThat(dbProps.getPool()).isNotNull();
        assertThat(dbProps.getPool().getMinSize()).isEqualTo(5);
        assertThat(dbProps.getPool().getMaxSize()).isEqualTo(20);
        assertThat(dbProps.getPool().getTimeout()).isEqualTo(30000L);
    }

    @Test
    void configurationService_apiPropertiesLoaded() {
        var apiProps = configurationService.getApiProperties();

        assertThat(apiProps.getBaseUrl()).isNotBlank();
        assertThat(apiProps.getTimeout()).isPositive();
        assertThat(apiProps.getRetryCount()).isGreaterThanOrEqualTo(0);
        assertThat(apiProps.getKey()).isNotBlank();
    }

    @Test
    void configurationService_providesFormattedInfo() {
        String dbInfo = configurationService.getDatabaseInfo();
        String apiInfo = configurationService.getApiInfo();

        assertThat(dbInfo).contains("Database:");
        assertThat(apiInfo).contains("API:");
    }
}

