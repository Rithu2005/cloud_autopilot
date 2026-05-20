package com.autoo.incident.controller;

import com.autoo.incident.entity.Incident;
import com.autoo.incident.service.IncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {

    @Autowired
    private IncidentService service;

    @GetMapping
    public List<Incident> getAllIncidents() {
        return service.getAllIncidents();
    }

    @PostMapping
    public Incident createIncident(@RequestBody Incident incident) {
        return service.createIncident(incident);
    }

    @PutMapping("/{id}/status")
    public Incident updateStatus(@PathVariable Long id, @RequestParam String status) {
        return service.updateIncidentStatus(id, status);
    }

    /**
     * Real-time CPU/RAM Monitoring API for SRE Dashboard
     */
    @GetMapping("/stats")
    public Map<String, Object> getSystemStats() {
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        Map<String, Object> stats = new HashMap<>();
        stats.put("cpuUsage", osBean.getCpuLoad() * 100);
        stats.put("freeMemory", osBean.getFreeMemorySize() / (1024 * 1024));
        stats.put("totalMemory", osBean.getTotalMemorySize() / (1024 * 1024));
        stats.put("osName", osBean.getName());
        return stats;
    }
}
