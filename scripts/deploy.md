# Deployment Guide - Cloud Incident AutoPilot X

## Prerequisites
- Docker & Docker Compose
- Kubernetes (Minikube or EKS)
- Terraform
- AWS CLI (configured)
- Maven 3.8+
- Node.js 18+

## Step 1: Local Development (Docker Compose)
1. Build all services:
   ```bash
   mvn clean package -DskipTests
   ```
2. Start the stack:
   ```bash
   docker-compose up --build -d
   ```
3. Access Frontend: `http://localhost:3000`
4. Access API Gateway: `http://localhost:8080`

## Step 2: Infrastructure Provisioning (Terraform)
1. Navigate to terraform folder:
   ```bash
   cd terraform
   ```
2. Initialize and Apply:
   ```bash
   terraform init
   terraform apply -var="key_name=your-key"
   ```

## Step 3: Kubernetes Deployment
1. Create Namespace:
   ```bash
   kubectl apply -f kubernetes/common.yaml
   ```
2. Deploy Services:
   ```bash
   kubectl apply -f kubernetes/deployments.yaml
   ```
3. Setup Scaling & Ingress:
   ```bash
   kubectl apply -f kubernetes/hpa-ingress.yaml
   ```

## Step 4: Verification Commands
- Check Pods: `kubectl get pods -n autopilot`
- Check Services: `kubectl get svc -n autopilot`
- Check HPA: `kubectl get hpa -n autopilot`
- Check Logs: `kubectl logs -f <pod-name> -n autopilot`
