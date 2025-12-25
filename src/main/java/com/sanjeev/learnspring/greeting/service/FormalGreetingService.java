package com.sanjeev.learnspring.greeting.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("formalGreetingService")
public class FormalGreetingService implements GreetingService {
    @Override
    public String greet(String name) {
        return "Good day, " + name + ".";
    }
}

