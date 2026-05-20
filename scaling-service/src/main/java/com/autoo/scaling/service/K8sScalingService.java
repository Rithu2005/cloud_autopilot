package com.autoo.scaling.service;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.AutoscalingV1Api;
import io.kubernetes.client.openapi.models.V1HorizontalPodAutoscaler;
import io.kubernetes.client.util.Config;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class K8sScalingService {

    private AutoscalingV1Api api;

    @PostConstruct
    public void init() {
        try {
            ApiClient client = Config.defaultClient();
            Configuration.setDefaultApiClient(client);
            api = new AutoscalingV1Api();
        } catch (Exception e) {
            System.err.println("Could not initialize K8s client: " + e.getMessage());
        }
    }

    public String scaleDeployment(String namespace, String name, int replicas) {
        // In a real scenario, this would use the Kubernetes client to scale
        // For demonstration, we'll simulate the call
        return "Scaled deployment " + name + " in namespace " + namespace + " to " + replicas + " replicas";
    }

    public V1HorizontalPodAutoscaler getHpaStatus(String namespace, String name) {
        try {
            return api.readNamespacedHorizontalPodAutoscalerStatus(name, namespace, null);
        } catch (Exception e) {
            return null;
        }
    }
}
