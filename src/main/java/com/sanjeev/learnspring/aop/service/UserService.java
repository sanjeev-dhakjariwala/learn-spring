package com.sanjeev.learnspring.aop.service;

import com.sanjeev.learnspring.aop.annotation.Auditable;
import com.sanjeev.learnspring.aop.annotation.TrackExecutionTime;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * User service to demonstrate AOP with business logic.
 */
@Service
public class UserService {

    private final List<String> users = new ArrayList<>();

    public UserService() {
        users.add("Alice");
        users.add("Bob");
        users.add("Charlie");
    }

    @Auditable(action = "CREATE_USER", logArgs = true)
    public String createUser(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        users.add(username);
        return "User created: " + username;
    }

    @TrackExecutionTime("Fetch all users")
    public List<String> getAllUsers() {
        // Simulate database delay
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return new ArrayList<>(users);
    }

    @Auditable(action = "FIND_USER", logArgs = true, logResult = true)
    @TrackExecutionTime
    public String findUser(String username) {
        return users.stream()
                .filter(u -> u.equalsIgnoreCase(username))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
    }

    @Auditable(action = "DELETE_USER", logArgs = true, logResult = false)
    public boolean deleteUser(String username) {
        boolean removed = users.remove(username);
        if (!removed) {
            throw new IllegalArgumentException("User not found: " + username);
        }
        return true;
    }

    public int getUserCount() {
        return users.size();
    }
}

