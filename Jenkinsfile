pipeline {
    agent any

    stages {

        stage('Clone') {
            steps {
                git branch: 'main', url: 'https://github.com/Rithu2005/cloud_autopilot.git'
            }
        }

        stage('Build Docker Images') {
            steps {
                sh 'docker build -t discovery-service ./discovery-service'
                sh 'docker build -t auth-service ./auth-service'
                sh 'docker build -t incident-service ./incident-service'
                sh 'docker build -t monitoring-service ./monitoring-service'
                sh 'docker build -t notification-service ./notification-service'
                sh 'docker build -t scaling-service ./scaling-service'
                sh 'docker build -t api-gateway ./api-gateway'
                sh 'docker build -t frontend ./frontend'
            }
        }

        stage('Deploy Kubernetes') {
            steps {
                sh 'kubectl apply -f k8s/'
            }
        }
    }
}