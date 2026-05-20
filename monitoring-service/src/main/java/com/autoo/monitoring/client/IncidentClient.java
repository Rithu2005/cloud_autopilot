package com.autoo.monitoring.client;

import com.autoo.monitoring.dto.IncidentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class IncidentClient {

    private final RestTemplate restTemplate;
    private final String gatewayUrl;

    public IncidentClient(RestTemplate restTemplate, 
                          @Value("${gateway.url:http://localhost:8080}") String gatewayUrl) {
        this.restTemplate = restTemplate;
        this.gatewayUrl = gatewayUrl;
    }

    public void createIncident(IncidentRequest request) {
        String url = gatewayUrl + "/api/incidents";
        try {
            log.info("[INCIDENT CLIENT] Calling API Gateway to create incident: {}", request.getTitle());
            restTemplate.postForObject(url, request, Object.class);
            log.info("[INCIDENT CLIENT] Successfully created incident via Gateway");
        } catch (Exception e) {
            log.error("[INCIDENT ERROR] Failed to create incident via Gateway. Incident Service might be unavailable. Error: {}", e.getMessage());
        }
    }
}
