package com.autoo.incident.service;

import com.autoo.incident.entity.Incident;
import com.autoo.incident.repository.IncidentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class IncidentService {

    @Autowired
    private IncidentRepository repository;

    public List<Incident> getAllIncidents() {
        return repository.findAll();
    }

    public Incident createIncident(Incident incident) {
        incident.setCreatedAt(LocalDateTime.now());
        if (incident.getStatus() == null) {
            incident.setStatus("OPEN");
        }
        log.info("Creating new incident: {}", incident.getTitle());
        return repository.save(incident);
    }

    public Incident updateIncidentStatus(Long id, String status) {
        Incident incident = repository.findById(id).orElseThrow(() -> new RuntimeException("Incident not found"));
        incident.setStatus(status);
        log.info("Updated incident {} to status {}", id, status);
        return repository.save(incident);
    }

    /**
     * AI-Powered Auto-Healing Simulation Logic
     * Automatically resolves "LOW" severity incidents every 60 seconds
     */
    @Scheduled(fixedRate = 60000)
    public void performAutoHealing() {
        List<Incident> openIncidents = repository.findAll();
        openIncidents.stream()
                .filter(inc -> "OPEN".equals(inc.getStatus()) && "LOW".equals(inc.getSeverity()))
                .forEach(inc -> {
                    inc.setStatus("RESOLVED");
                    inc.setDescription(inc.getDescription() + " | Auto-Healed by AutoPilot X");
                    repository.save(inc);
                    log.info("Auto-Healed incident: {}", inc.getTitle());
                });
    }
}
