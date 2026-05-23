# Cloud AutoPilot X — Autonomous Cloud SRE Monitoring Platform

## Project Overview
Cloud AutoPilot X is a production-grade, autonomous observability and incident response platform. It leverages Spring Boot microservices, React, and a full cloud-native DevOps stack to monitor, detect, and autonomously respond to infrastructure failures.

### Tech Stack
- **Backend**: Spring Boot 3, Java 17, Spring Cloud (Eureka, Gateway)
- **Frontend**: React 18, Tailwind CSS, Framer Motion
- **Database**: PostgreSQL
- **Observability**: Prometheus, Grafana, Micrometer
- **Infrastructure**: Terraform, AWS (VPC, EC2, ECR, S3)
- **Orchestration**: Kubernetes (K3s/Minikube), Docker
- **CI/CD**: GitHub Actions

---

## 🚀 Deployment Guide

### 1. Prerequisites
- AWS Account (Free Tier)
- Terraform installed
- Docker & Docker Compose
- kubectl installed
- GitHub account (for CI/CD)

### 2. Infrastructure Provisioning (Terraform)
```bash
cd terraform
terraform init
terraform plan
terraform apply -auto-approve
```

### 3. Local Development (Docker Compose)
```bash
docker-compose up --build -d
```

### 4. Kubernetes Deployment
```bash
# Create namespace and base config
kubectl apply -f k8s/namespace/

# Deploy stateful services
kubectl apply -f k8s/postgres/
kubectl apply -f k8s/prometheus/
kubectl apply -f k8s/grafana/

# Deploy microservices
kubectl apply -f k8s/discovery/
kubectl apply -f k8s/gateway/
kubectl apply -f k8s/incident/
kubectl apply -f k8s/monitoring/
kubectl apply -f k8s/frontend/

# Apply networking & scaling
kubectl apply -f k8s/ingress/
kubectl apply -f k8s/autoscaling/
```

---

## 🏗️ Architecture Explanation

1.  **Autonomous Engine**: The `monitoring-service` polls system health every 10 seconds. When thresholds are breached (e.g., CPU > 90%), it autonomously triggers incident creation via the `incident-service`.
2.  **Self-Healing**: Kubernetes HPA automatically scales pods based on CPU utilization, while liveness/readiness probes ensure traffic only reaches healthy instances.
3.  **Observability Stack**: Prometheus scrapes metrics from all Spring Boot Actuator endpoints. Grafana visualizes these metrics with pre-configured dashboards.
4.  **Security**: JWT-based authentication secures all microservice communication through the API Gateway. K8s Secrets protect database credentials and private keys.

---

## 🛠️ Verification & Troubleshooting

### Verification
- **Eureka Dashboard**: `http://<EC2_IP>:8761`
- **API Gateway**: `http://<EC2_IP>:8080`
- **Frontend Dashboard**: `http://<EC2_IP>:80`
- **Prometheus**: `http://<EC2_IP>:9090`
- **Grafana**: `http://<EC2_IP>:3002`

### Troubleshooting
- **Pod CrashLoopBackOff**: Check logs with `kubectl logs <pod_name> -n cloud-autopilot`.
- **Database Connection Refused**: Verify `postgres` service is running and credentials in `autopilot-secrets` match.
- **Scrape Failure**: Ensure annotations `prometheus.io/scrape: 'true'` are present on K8s services.

---

## 📄 Project Summary (Resume Ready)
**Cloud AutoPilot X | Lead DevOps & SRE Engineer**
- Architected an autonomous incident response platform using Spring Boot and React, reducing manual intervention for infrastructure spikes by 100%.
- Orchestrated 7+ microservices on Kubernetes (K3s) with Horizontal Pod Autoscaler and self-healing capabilities.
- Automated full infrastructure provisioning on AWS using modular Terraform (VPC, Security Groups, ECR, EC2).
- Implemented a production-grade CI/CD pipeline with GitHub Actions, automating multi-stage Docker builds and K8s deployments.
- Established a comprehensive observability stack with Prometheus and Grafana, monitoring JVM metrics and service health in real-time.

jenkins succesfull
