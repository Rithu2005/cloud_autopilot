package com.autoo.monitoring.dto;

public class MonitoringStats {
    private double cpuUsage;
    private double memoryUsage;
    private double diskUsage;
    private String riskLevel;
    private String status;

    public MonitoringStats(double cpuUsage, double memoryUsage, double diskUsage, String riskLevel, String status) {
        this.cpuUsage = cpuUsage;
        this.memoryUsage = memoryUsage;
        this.diskUsage = diskUsage;
        this.riskLevel = riskLevel;
        this.status = status;
    }

    public double getCpuUsage() { return cpuUsage; }
    public double getMemoryUsage() { return memoryUsage; }
    public double getDiskUsage() { return diskUsage; }
    public String getRiskLevel() { return riskLevel; }
    public String getStatus() { return status; }
}
