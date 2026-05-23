pipeline {
    agent any

    tools {
        maven 'Maven-3'
    }

    stages {

        stage('Check Repository') {
            steps {
                sh 'ls'
            }
        }

        stage('Build Jar') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Docker Version Check') {
            steps {
                sh 'docker --version || true'
            }
        }

        stage('Pipeline Success') {
            steps {
                echo 'AI Cloud Autopilot Pipeline Executed Successfully'
            }
        }
    }
}