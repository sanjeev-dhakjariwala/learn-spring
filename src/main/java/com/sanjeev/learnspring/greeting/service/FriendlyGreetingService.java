package com.sanjeev.learnspring.greeting.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class FriendlyGreetingService implements GreetingService {
    @Override
    public String greet(String name) {
        return "Hey " + name + "!";
    }
}

