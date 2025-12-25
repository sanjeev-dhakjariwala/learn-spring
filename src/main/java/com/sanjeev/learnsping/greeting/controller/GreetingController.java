package com.sanjeev.learnsping.greeting.controller;

import com.sanjeev.learnsping.greeting.dto.GreetingResponse;
import com.sanjeev.learnsping.greeting.service.GreetingService;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/greetings")
public class GreetingController {

    private final GreetingService greetingService;
    private final GreetingService formalGreetingService;

    public GreetingController(GreetingService greetingService,
                              @Qualifier("formalGreetingService") GreetingService formalGreetingService) {
        this.greetingService = greetingService;
        this.formalGreetingService = formalGreetingService;
    }

    @GetMapping
    public GreetingResponse greet(@RequestParam(defaultValue = "World") String name) {
        String message = greetingService.greet(name);
        return new GreetingResponse(message, "friendly");
    }

    @GetMapping("/formal")
    public GreetingResponse greetFormally(@RequestParam(defaultValue = "World") String name) {
        String message = formalGreetingService.greet(name);
        return new GreetingResponse(message, "formal");
    }
}

