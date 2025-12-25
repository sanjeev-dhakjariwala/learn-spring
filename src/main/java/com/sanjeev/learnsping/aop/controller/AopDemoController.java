package com.sanjeev.learnsping.aop.controller;

import com.sanjeev.learnsping.aop.service.CalculationService;
import com.sanjeev.learnsping.aop.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST controller demonstrating AOP in action.
 * All methods are intercepted by PerformanceAspect for tracking.
 */
@RestController
@RequestMapping("/api/aop")
public class AopDemoController {

    private final CalculationService calculationService;
    private final UserService userService;

    public AopDemoController(CalculationService calculationService, UserService userService) {
        this.calculationService = calculationService;
        this.userService = userService;
    }

    @GetMapping("/calculate/add")
    public Map<String, Object> add(@RequestParam int a, @RequestParam int b) {
        int result = calculationService.add(a, b);
        return createResponse("addition", a, b, result);
    }

    @GetMapping("/calculate/add-delayed")
    public Map<String, Object> addWithDelay(@RequestParam int a, @RequestParam int b) {
        int result = calculationService.addWithDelay(a, b);
        return createResponse("addition-with-delay", a, b, result);
    }

    @GetMapping("/calculate/multiply")
    public Map<String, Object> multiply(@RequestParam int a, @RequestParam int b) {
        int result = calculationService.multiply(a, b);
        return createResponse("multiplication", a, b, result);
    }

    @GetMapping("/calculate/divide")
    public Map<String, Object> divide(@RequestParam int a, @RequestParam int b) {
        try {
            int result = calculationService.divide(a, b);
            return createResponse("division", a, b, result);
        } catch (ArithmeticException ex) {
            return Map.of("error", ex.getMessage());
        }
    }

    @PostMapping("/calculate/complex")
    public Map<String, Object> complexCalculation(@RequestBody Map<String, String> request) {
        String input = request.getOrDefault("input", "test");
        String result = calculationService.complexCalculation(input);
        return Map.of("input", input, "result", result);
    }

    @GetMapping("/users")
    public Map<String, Object> getAllUsers() {
        List<String> users = userService.getAllUsers();
        return Map.of("users", users, "count", users.size());
    }

    @PostMapping("/users")
    public Map<String, Object> createUser(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String result = userService.createUser(username);
        return Map.of("message", result);
    }

    @GetMapping("/users/{username}")
    public Map<String, Object> findUser(@PathVariable String username) {
        try {
            String user = userService.findUser(username);
            return Map.of("found", true, "user", user);
        } catch (IllegalArgumentException ex) {
            return Map.of("found", false, "error", ex.getMessage());
        }
    }

    @DeleteMapping("/users/{username}")
    public Map<String, Object> deleteUser(@PathVariable String username) {
        try {
            userService.deleteUser(username);
            return Map.of("deleted", true, "username", username);
        } catch (IllegalArgumentException ex) {
            return Map.of("deleted", false, "error", ex.getMessage());
        }
    }

    @GetMapping("/demo")
    public Map<String, Object> runFullDemo() {
        Map<String, Object> response = new HashMap<>();

        // Demonstrate calculations
        response.put("add", calculationService.add(5, 3));
        response.put("multiply", calculationService.multiply(4, 7));
        response.put("complex", calculationService.complexCalculation("hello world"));

        // Demonstrate user operations
        response.put("users", userService.getAllUsers());
        response.put("userCount", userService.getUserCount());

        return response;
    }

    private Map<String, Object> createResponse(String operation, int a, int b, int result) {
        return Map.of(
                "operation", operation,
                "operand1", a,
                "operand2", b,
                "result", result
        );
    }
}

