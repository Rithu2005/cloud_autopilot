package com.autoo.scaling.controller;

import com.autoo.scaling.service.K8sScalingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/scaling")
public class ScalingController {

    @Autowired
    private K8sScalingService scalingService;

    @PostMapping("/scale")
    public String scale(@RequestParam String namespace, @RequestParam String name, @RequestParam int replicas) {
        return scalingService.scaleDeployment(namespace, name, replicas);
    }

    @GetMapping("/hpa")
    public Object getHpa(@RequestParam String namespace, @RequestParam String name) {
        return scalingService.getHpaStatus(namespace, name);
    }
}
