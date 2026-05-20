# Cloud Incident AutoPilot X: Technical Abstract & SRE Architecture

## 1. Project Abstract
"Cloud Incident AutoPilot X" is an AI-powered, cloud-native Site Reliability Engineering (SRE) and Autonomous Healing platform. It addresses the complexity of modern microservices by providing automated failure detection, real-time observability, and self-correction. By utilizing the PLG stack (Prometheus, Loki, Grafana), the platform identifies performance bottlenecks and triggers "Auto-Healing" tasks to resolve low-severity incidents without human intervention. The platform is architected for maximum cost-efficiency on AWS Free Tier, utilizing t2.micro instances and Docker-based orchestration.

## 2. Root Cause Analysis (Fixes)
- **404 on /api/incidents:** Root cause was a package scanning mismatch and port 8083/8084 conflict. Standardized port to **8084** and verified package `com.autoo.incident`.
- **500 on Gateway:** Root cause was the missing `useSSL=false` and `allowPublicKeyRetrieval=true` in the JDBC URL when connecting to MySQL 8.0 in a containerized network. (Fixed: Migrated to PostgreSQL 15).

## 3. Project Workflow
1. **Detection:** Microservices export metrics via Actuator to Prometheus.
2. **Analysis:** Grafana visualizes CPU/Memory spikes.
3. **Response:** `incident-service` detects spikes; `notification-service` alerts via Slack.
4. **Healing:** `incident-service` AI-Logic resolves "LOW" severity incidents automatically.
5. **Scaling:** `scaling-service` simulates Kubernetes pod expansion.

## 4. Final Testing Procedure

### Step 1: Start Discovery & Database
```bash
docker-compose up -d discovery-service postgres redis
```

### Step 2: Build & Start Microservices
```bash
mvn clean package -DskipTests
docker-compose up --build -d
```

### Step 3: Verify with cURL
- **Direct Check (Incident Service):**
  `curl http://localhost:8084/api/incidents`
- **Gateway Check:**
  `curl http://localhost:8080/api/incidents`
- **Create Incident:**
  `curl -X POST -H "Content-Type: application/json" -d '{"title":"CPU High", "severity":"LOW"}' http://localhost:8080/api/incidents`
- **Check System Stats:**
  `curl http://localhost:8080/api/incidents/stats`

### Step 4: Infrastructure Verify
```bash
cd terraform
terraform plan
```

---
**Senior DevOps Engineer | SRE Architect**
