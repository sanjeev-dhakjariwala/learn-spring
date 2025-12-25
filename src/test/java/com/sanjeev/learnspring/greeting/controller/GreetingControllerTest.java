package com.sanjeev.learnspring.greeting.controller;

import com.sanjeev.learnspring.greeting.dto.GreetingResponse;
import com.sanjeev.learnspring.greeting.service.FormalGreetingService;
import com.sanjeev.learnspring.greeting.service.FriendlyGreetingService;
import com.sanjeev.learnspring.greeting.service.GreetingService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GreetingControllerTest {

    @Autowired
    private GreetingController controller;

    @Autowired
    private GreetingService greetingService;

    @Autowired
    @Qualifier("formalGreetingService")
    private GreetingService formalGreetingService;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    void greet_returnsFriendlyMessage() {
        GreetingResponse response = controller.greet("Sanjeev");
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo("Hey Sanjeev!");
        assertThat(response.getType()).isEqualTo("friendly");
    }

    @Test
    void greetFormally_returnsFormalMessage() {
        GreetingResponse response = controller.greetFormally("Sanjeev");
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo("Good day, Sanjeev.");
        assertThat(response.getType()).isEqualTo("formal");
    }

    @Test
    void verifyDependencyInjection_primaryBeanIsInjected() {
        // Verify that the primary bean (FriendlyGreetingService) is injected
        assertThat(greetingService).isInstanceOf(FriendlyGreetingService.class);
        assertThat(greetingService.greet("Test")).isEqualTo("Hey Test!");
    }

    @Test
    void verifyDependencyInjection_qualifiedBeanIsInjected() {
        // Verify that the qualified bean (FormalGreetingService) is injected
        assertThat(formalGreetingService).isInstanceOf(FormalGreetingService.class);
        assertThat(formalGreetingService.greet("Test")).isEqualTo("Good day, Test.");
    }
}

