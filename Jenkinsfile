pipeline {
    agent any

    stages {

        stage('Clone') {
            steps {
                git 'https://github.com/Rithu2005/cloud_autopilot.git'
            }
        }

        stage('Build Docker Images') {
            steps {
                bat 'docker build -t discovery-service ./discovery-service'
                bat 'docker build -t auth-service ./auth-service'
                bat 'docker build -t incident-service ./incident-service'
                bat 'docker build -t monitoring-service ./monitoring-service'
                bat 'docker build -t notification-service ./notification-service'
                bat 'docker build -t scaling-service ./scaling-service'
                bat 'docker build -t api-gateway ./api-gateway'
            }
        }

        stage('Deploy Kubernetes') {
            steps {
                bat 'kubectl apply -f k8s/'
            }
        }
    }
}