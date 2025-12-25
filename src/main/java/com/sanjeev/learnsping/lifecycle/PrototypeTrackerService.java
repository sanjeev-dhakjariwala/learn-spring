package com.sanjeev.learnsping.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton service that tracks prototype bean instances.
 * Demonstrates how a singleton can request multiple prototype instances.
 */
@Service
public class PrototypeTrackerService {
    private static final Logger log = LoggerFactory.getLogger(PrototypeTrackerService.class);
    private final ApplicationContext context;
    private final List<Integer> trackedInstances;

    public PrototypeTrackerService(ApplicationContext context) {
        this.context = context;
        this.trackedInstances = new ArrayList<>();
        log.info("PrototypeTrackerService created");
    }

    @PostConstruct
    public void init() {
        log.info("PrototypeTrackerService @PostConstruct - Ready to track prototype instances");
    }

    @PreDestroy
    public void destroy() {
        log.info("PrototypeTrackerService @PreDestroy - Tracked {} prototype instances", trackedInstances.size());
    }

    /**
     * Creates a new prototype instance and tracks it.
     */
    public PrototypeService createPrototypeInstance() {
        log.info("Requesting new PrototypeService instance from ApplicationContext");
        PrototypeService prototype = context.getBean(PrototypeService.class);
        trackedInstances.add(prototype.getInstanceId());
        log.info("Tracked prototype instance #{}", prototype.getInstanceId());
        return prototype;
    }

    public List<Integer> getTrackedInstances() {
        return new ArrayList<>(trackedInstances);
    }

    public int getTotalInstancesCreated() {
        return trackedInstances.size();
    }
}

