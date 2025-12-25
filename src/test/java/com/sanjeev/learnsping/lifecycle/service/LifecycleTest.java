package com.sanjeev.learnsping.lifecycle.service;

import com.sanjeev.learnsping.lifecycle.controller.LifecycleController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LifecycleTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private SingletonService singletonService1;

    @Autowired
    private SingletonService singletonService2;

    @Autowired
    private PrototypeTrackerService trackerService;

    @Test
    void contextLoads() {
        assertThat(context).isNotNull();
    }

    @Test
    void singletonScope_returnsSameInstance() {
        // When injected multiple times, singleton returns the same instance
        assertThat(singletonService1).isSameAs(singletonService2);
        assertThat(singletonService1.getInstanceId())
                .isEqualTo(singletonService2.getInstanceId());
    }

    @Test
    void singletonScope_manualLookupReturnsSameInstance() {
        // Even when manually retrieved from context
        SingletonService lookup1 = context.getBean(SingletonService.class);
        SingletonService lookup2 = context.getBean(SingletonService.class);

        assertThat(lookup1).isSameAs(lookup2);
        assertThat(lookup1).isSameAs(singletonService1);
    }

    @Test
    void prototypeScope_createsNewInstanceEachTime() {
        // Each lookup creates a new prototype instance
        PrototypeService proto1 = context.getBean(PrototypeService.class);
        PrototypeService proto2 = context.getBean(PrototypeService.class);
        PrototypeService proto3 = context.getBean(PrototypeService.class);

        // Different instances
        assertThat(proto1).isNotSameAs(proto2);
        assertThat(proto2).isNotSameAs(proto3);
        assertThat(proto1).isNotSameAs(proto3);

        // Different instance IDs
        assertThat(proto1.getInstanceId()).isNotEqualTo(proto2.getInstanceId());
        assertThat(proto2.getInstanceId()).isNotEqualTo(proto3.getInstanceId());
    }

    @Test
    void prototypeTrackerService_tracksMultipleInstances() {
        int initialCount = trackerService.getTotalInstancesCreated();

        PrototypeService proto1 = trackerService.createPrototypeInstance();
        PrototypeService proto2 = trackerService.createPrototypeInstance();

        assertThat(trackerService.getTotalInstancesCreated()).isEqualTo(initialCount + 2);
        assertThat(proto1.getInstanceId()).isNotEqualTo(proto2.getInstanceId());

        assertThat(trackerService.getTrackedInstances())
                .contains(proto1.getInstanceId(), proto2.getInstanceId());
    }

    @Test
    void lifecycleController_singletonReturnsConsistentData() {
        LifecycleController controller = context.getBean(LifecycleController.class);

        var response1 = controller.getSingleton();
        var response2 = controller.getSingleton();

        // Same singleton instance ID every time
        assertThat(response1.get("instanceId")).isEqualTo(response2.get("instanceId"));
    }

    @Test
    void lifecycleController_prototypeCreatesNewInstances() {
        LifecycleController controller = context.getBean(LifecycleController.class);

        var response1 = controller.getPrototype();
        var response2 = controller.getPrototype();

        // Different prototype instance IDs each time
        assertThat(response1.get("instanceId")).isNotEqualTo(response2.get("instanceId"));
    }

    @Test
    void lifecycleController_demoShowsBothScopes() {
        LifecycleController controller = context.getBean(LifecycleController.class);

        var response = controller.runDemo();

        // Singleton messages are the same
        assertThat(response.get("singleton1")).isEqualTo(response.get("singleton2"));

        // Prototype messages have different instance IDs
        String proto1 = (String) response.get("prototype1");
        String proto2 = (String) response.get("prototype2");
        String proto3 = (String) response.get("prototype3");

        assertThat(proto1).isNotEqualTo(proto2);
        assertThat(proto2).isNotEqualTo(proto3);
    }
}

