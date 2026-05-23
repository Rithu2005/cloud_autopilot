pipeline {
    agent any

    stages {

        stage('Clone Check') {
            steps {
                bat 'dir'
            }
        }

        stage('Docker Check') {
            steps {
                bat 'docker ps'
            }
        }

        stage('Kubernetes Check') {
            steps {
                bat 'kubectl get pods'
            }
        }

        stage('Build Success') {
            steps {
                echo 'AI Cloud Autopilot Pipeline Success'
            }
        }
    }
}