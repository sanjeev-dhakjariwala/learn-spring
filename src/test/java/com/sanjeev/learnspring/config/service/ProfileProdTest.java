package com.sanjeev.learnspring.config.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test with prod profile active to verify profile-specific beans.
 */
@SpringBootTest
@ActiveProfiles("prod")
class ProfileProdTest {

    @Autowired
    private EnvironmentService environmentService;

    @Autowired
    private AppInfoService appInfoService;

    @Test
    void prodProfile_loadsProdEnvironmentService() {
        assertThat(environmentService).isInstanceOf(ProdEnvironmentService.class);
        assertThat(environmentService.getEnvironmentName()).isEqualTo("Production");
        assertThat(environmentService.isDebugEnabled()).isFalse();
        assertThat(environmentService.getLogLevel()).isEqualTo("INFO");
    }

    @Test
    void prodProfile_overridesProperties() {
        // Prod profile should override app.name
        assertThat(appInfoService.getAppName()).contains("PROD");
    }
}

