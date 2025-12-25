package com.sanjeev.learnspring.lifecycle.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SingletonService {
    private static final Logger log = LoggerFactory.getLogger(SingletonService.class);
    private static final AtomicInteger instanceCounter = new AtomicInteger(0);
    private final int instanceId;

    public SingletonService() {
        this.instanceId = instanceCounter.incrementAndGet();
        log.info("SingletonService constructor called - Instance #{}", instanceId);
    }

    @PostConstruct
    public void init() {
        log.info("SingletonService @PostConstruct called - Instance #{}", instanceId);
    }

    @PreDestroy
    public void destroy() {
        log.info("SingletonService @PreDestroy called - Instance #{}", instanceId);
    }

    public String getMessage() {
        return "Singleton Service - Instance #" + instanceId;
    }

    public int getInstanceId() {
        return instanceId;
    }
}

