package com.sanjeev.learnspring.lifecycle.controller;

import com.sanjeev.learnspring.lifecycle.service.PrototypeService;
import com.sanjeev.learnspring.lifecycle.service.PrototypeTrackerService;
import com.sanjeev.learnspring.lifecycle.service.SingletonService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/lifecycle")
public class LifecycleController {

    private final SingletonService singletonService;
    private final PrototypeTrackerService trackerService;

    public LifecycleController(SingletonService singletonService,
                               PrototypeTrackerService trackerService) {
        this.singletonService = singletonService;
        this.trackerService = trackerService;
    }

    @GetMapping("/singleton")
    public Map<String, Object> getSingleton() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", singletonService.getMessage());
        response.put("instanceId", singletonService.getInstanceId());
        response.put("scope", "singleton");
        response.put("note", "Same instance returned every time");
        return response;
    }

    @GetMapping("/prototype")
    public Map<String, Object> getPrototype() {
        PrototypeService prototype = trackerService.createPrototypeInstance();
        Map<String, Object> response = new HashMap<>();
        response.put("message", prototype.getMessage());
        response.put("instanceId", prototype.getInstanceId());
        response.put("scope", "prototype");
        response.put("note", "New instance created each time");
        return response;
    }

    @GetMapping("/tracker")
    public Map<String, Object> getTrackerInfo() {
        Map<String, Object> response = new HashMap<>();
        response.put("totalPrototypeInstancesCreated", trackerService.getTotalInstancesCreated());
        response.put("trackedInstanceIds", trackerService.getTrackedInstances());
        response.put("singletonInstanceId", singletonService.getInstanceId());
        response.put("note", "Tracker is singleton, but creates multiple prototypes");
        return response;
    }

    @GetMapping("/demo")
    public Map<String, Object> runDemo() {
        Map<String, Object> response = new HashMap<>();

        // Singleton - same instance every time
        response.put("singleton1", singletonService.getMessage());
        response.put("singleton2", singletonService.getMessage());
        response.put("singletonNote", "Both calls return the same instance");

        // Prototype - new instance every time
        PrototypeService proto1 = trackerService.createPrototypeInstance();
        PrototypeService proto2 = trackerService.createPrototypeInstance();
        PrototypeService proto3 = trackerService.createPrototypeInstance();

        response.put("prototype1", proto1.getMessage());
        response.put("prototype2", proto2.getMessage());
        response.put("prototype3", proto3.getMessage());
        response.put("prototypeNote", "Each call creates a new instance");

        response.put("totalPrototypesCreated", trackerService.getTotalInstancesCreated());

        return response;
    }
}

