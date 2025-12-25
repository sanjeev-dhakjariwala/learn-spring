package com.sanjeev.learnspring.greeting.controller;

import com.sanjeev.learnspring.greeting.service.GreetingService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/greetings")
public class GreetingViewController {

    private final GreetingService greetingService;
    private final GreetingService formalGreetingService;

    public GreetingViewController(GreetingService greetingService,
                                  @Qualifier("formalGreetingService") GreetingService formalGreetingService) {
        this.greetingService = greetingService;
        this.formalGreetingService = formalGreetingService;
    }

    @GetMapping
    public String greetingPage(Model model) {
        model.addAttribute("pageTitle", "Greeting Service");
        return "greeting";
    }

    @GetMapping("/friendly")
    public String friendlyGreeting(@RequestParam(defaultValue = "World") String name, Model model) {
        String message = greetingService.greet(name);
        model.addAttribute("message", message);
        model.addAttribute("type", "Friendly");
        model.addAttribute("name", name);
        return "greeting-result";
    }

    @GetMapping("/formal")
    public String formalGreeting(@RequestParam(defaultValue = "World") String name, Model model) {
        String message = formalGreetingService.greet(name);
        model.addAttribute("message", message);
        model.addAttribute("type", "Formal");
        model.addAttribute("name", name);
        return "greeting-result";
    }
}

