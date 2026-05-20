package com.autoo.monitoring.scheduler;

import com.autoo.monitoring.service.IncidentAutomationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MonitoringScheduler {

    private final IncidentAutomationService automationService;

    public MonitoringScheduler(IncidentAutomationService automationService) {
        this.automationService = automationService;
    }

    @Scheduled(fixedRate = 10000) // Run every 10 seconds
    public void monitorSystem() {
        try {
            automationService.evaluateMetrics();
        } catch (Exception e) {
            log.error("[MONITOR ERROR] Unexpected error during monitoring cycle: {}", e.getMessage());
        }
    }
}
