package com.sanjeev.learnsping.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@Scope("prototype")
public class PrototypeService {
    private static final Logger log = LoggerFactory.getLogger(PrototypeService.class);
    private static final AtomicInteger instanceCounter = new AtomicInteger(0);
    private final int instanceId;

    public PrototypeService() {
        this.instanceId = instanceCounter.incrementAndGet();
        log.info("PrototypeService constructor called - Instance #{}", instanceId);
    }

    @PostConstruct
    public void init() {
        log.info("PrototypeService @PostConstruct called - Instance #{}", instanceId);
    }

    @PreDestroy
    public void destroy() {
        // This will NOT be called by Spring for prototype beans!
        log.warn("PrototypeService @PreDestroy called - Instance #{} (This should NOT happen!)", instanceId);
    }

    public String getMessage() {
        return "Prototype Service - Instance #" + instanceId;
    }

    public int getInstanceId() {
        return instanceId;
    }
}

