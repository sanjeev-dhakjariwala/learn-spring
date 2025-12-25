package com.sanjeev.learnsping.config.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test with dev profile active to verify profile-specific beans.
 */
@SpringBootTest
@ActiveProfiles("dev")
class ProfileDevTest {

    @Autowired
    private EnvironmentService environmentService;

    @Autowired
    private AppInfoService appInfoService;

    @Test
    void devProfile_loadsDevEnvironmentService() {
        assertThat(environmentService).isInstanceOf(DevEnvironmentService.class);
        assertThat(environmentService.getEnvironmentName()).isEqualTo("Development");
        assertThat(environmentService.isDebugEnabled()).isTrue();
        assertThat(environmentService.getLogLevel()).isEqualTo("DEBUG");
    }

    @Test
    void devProfile_overridesProperties() {
        // Dev profile should override app.name
        assertThat(appInfoService.getAppName()).contains("DEV");
    }
}

