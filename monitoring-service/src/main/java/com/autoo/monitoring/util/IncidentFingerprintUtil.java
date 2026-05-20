package com.autoo.monitoring.util;

public class IncidentFingerprintUtil {
    
    public static final String CPU_HIGH = "CPU_HIGH";
    public static final String CPU_CRITICAL = "CPU_CRITICAL";
    public static final String RAM_HIGH = "RAM_HIGH";
    public static final String RAM_CRITICAL = "RAM_CRITICAL";
    public static final String DISK_CRITICAL = "DISK_CRITICAL";

    public static String getFingerprint(String metric, String severity) {
        return metric.toUpperCase() + "_" + severity.toUpperCase();
    }
}
