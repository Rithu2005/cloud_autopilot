package com.autoo.monitoring.service;

import com.autoo.monitoring.dto.MonitoringStats;
import com.sun.management.OperatingSystemMXBean;
import org.springframework.stereotype.Service;

import java.io.File;
import java.lang.management.ManagementFactory;

@Service
public class MonitoringService {

    public MonitoringStats getStats() {
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        
        double cpuLoad = osBean.getSystemCpuLoad() * 100;
        double totalMemory = osBean.getTotalPhysicalMemorySize();
        double freeMemory = osBean.getFreePhysicalMemorySize();
        double memoryUsage = ((totalMemory - freeMemory) / totalMemory) * 100;
        
        File root = new File("/");
        double diskUsage = ((root.getTotalSpace() - root.getFreeSpace()) / (double) root.getTotalSpace()) * 100;

        String riskLevel;
        if (cpuLoad < 60 && memoryUsage < 60) {
            riskLevel = "HEALTHY";
        } else if (cpuLoad >= 85 || memoryUsage >= 85) {
            riskLevel = "HIGH RISK";
        } else {
            riskLevel = "MEDIUM RISK";
        }

        return new MonitoringStats(cpuLoad, memoryUsage, diskUsage, riskLevel, "RUNNING");
    }
}
