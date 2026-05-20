package com.autoo.monitoring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IncidentRequest {
    private String title;
    private String description;
    private String severity;
    private String status;
    private String affectedService;
}
