package com.autoo.monitoring.controller;

import com.autoo.monitoring.dto.MonitoringStats;
import com.autoo.monitoring.service.MonitoringService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/monitor")
public class MonitoringController {

    private final MonitoringService monitoringService;

    public MonitoringController(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @GetMapping("/stats")
    public MonitoringStats getStats() {
        return monitoringService.getStats();
    }
}
