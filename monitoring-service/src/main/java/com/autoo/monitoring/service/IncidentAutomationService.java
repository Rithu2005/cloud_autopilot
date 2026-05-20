package com.autoo.monitoring.service;

import com.autoo.monitoring.client.IncidentClient;
import com.autoo.monitoring.dto.IncidentRequest;
import com.autoo.monitoring.dto.MonitoringStats;
import com.autoo.monitoring.util.IncidentFingerprintUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class IncidentAutomationService {

    private final IncidentClient incidentClient;
    private final MonitoringService monitoringService;

    // Cooldown system: Key = Fingerprint, Value = Timestamp of last incident
    private final Map<String, Long> activeCooldowns = new ConcurrentHashMap<>();
    private static final long COOLDOWN_MINUTES = 5;

    public IncidentAutomationService(IncidentClient incidentClient, MonitoringService monitoringService) {
        this.incidentClient = incidentClient;
        this.monitoringService = monitoringService;
    }

    public void evaluateMetrics() {
        MonitoringStats stats = monitoringService.getStats();
        
        double cpu = normalizeMetric(stats.getCpuUsage());
        double ram = normalizeMetric(stats.getMemoryUsage());
        double disk = normalizeMetric(stats.getDiskUsage());

        log.info("[MONITOR] CPU={}%, RAM={}%, DISK={}%", 
                String.format("%.1f", cpu), 
                String.format("%.1f", ram), 
                String.format("%.1f", disk));

        evaluateCpu(cpu);
        evaluateRam(ram);
        evaluateDisk(disk);
        
        cleanupExpiredFingerprints();
    }

    private void evaluateCpu(double usage) {
        String severity = determineCpuSeverity(usage);
        String fingerprint = IncidentFingerprintUtil.getFingerprint("CPU", severity);

        if (severity != null) {
            log.warn("[THRESHOLD BREACH] CPU exceeded {} threshold", severity);
            triggerIncident("CPU", usage, severity, fingerprint);
        } else {
            handleRecovery("CPU");
        }
    }

    private void evaluateRam(double usage) {
        String severity = determineRamSeverity(usage);
        String fingerprint = IncidentFingerprintUtil.getFingerprint("RAM", severity);

        if (severity != null) {
            log.warn("[THRESHOLD BREACH] RAM exceeded {} threshold", severity);
            triggerIncident("RAM", usage, severity, fingerprint);
        } else {
            handleRecovery("RAM");
        }
    }

    private void evaluateDisk(double usage) {
        String severity = determineDiskSeverity(usage);
        String fingerprint = IncidentFingerprintUtil.getFingerprint("DISK", severity);

        if (severity != null) {
            log.warn("[THRESHOLD BREACH] DISK exceeded {} threshold", severity);
            triggerIncident("DISK", usage, severity, fingerprint);
        } else {
            handleRecovery("DISK");
        }
    }

    private void triggerIncident(String metric, double usage, String severity, String fingerprint) {
        if (isCooldownActive(fingerprint)) {
            log.info("[DUPLICATE BLOCKED] Skipping duplicate incident for {}", fingerprint);
            return;
        }

        IncidentRequest request = IncidentRequest.builder()
                .title(severity + " " + metric + " Spike")
                .description(metric + " usage exceeded threshold: " + String.format("%.1f", usage) + "%")
                .severity(severity)
                .status("OPEN")
                .affectedService("Cloud-Infrastructure")
                .build();

        incidentClient.createIncident(request);
        activeCooldowns.put(fingerprint, System.currentTimeMillis());
        log.info("[INCIDENT CREATED] Fingerprint={}", fingerprint);
    }

    private void handleRecovery(String metric) {
        // Simple recovery detection: if any critical/high fingerprint for this metric is active, clear it
        boolean recovered = activeCooldowns.keySet().removeIf(key -> key.startsWith(metric.toUpperCase()));
        if (recovered) {
            log.info("[RECOVERY] {} usage returned to healthy range", metric);
        }
    }

    private String determineCpuSeverity(double usage) {
        if (usage > 90) return "CRITICAL";
        if (usage > 80) return "HIGH";
        return null;
    }

    private String determineRamSeverity(double usage) {
        if (usage > 95) return "CRITICAL";
        if (usage > 85) return "HIGH";
        return null;
    }

    private String determineDiskSeverity(double usage) {
        if (usage > 90) return "CRITICAL";
        return null;
    }

    private boolean isCooldownActive(String fingerprint) {
        Long lastTriggered = activeCooldowns.get(fingerprint);
        if (lastTriggered == null) return false;

        long elapsedMillis = System.currentTimeMillis() - lastTriggered;
        return elapsedMillis < TimeUnit.MINUTES.toMillis(COOLDOWN_MINUTES);
    }

    private void cleanupExpiredFingerprints() {
        long now = System.currentTimeMillis();
        activeCooldowns.entrySet().removeIf(entry -> 
                (now - entry.getValue()) > TimeUnit.MINUTES.toMillis(COOLDOWN_MINUTES));
    }

    private double normalizeMetric(double value) {
        if (Double.isNaN(value) || value < 0) return 0.0;
        if (value > 100) return 100.0;
        return value;
    }
}
