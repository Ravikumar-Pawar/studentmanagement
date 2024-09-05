pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Checkout code from GitHub using token authentication
                git credentialsId: 'github-token', url: 'https://github.com/Ravikumar-Pawar/studentmanagement.git'
            }
        }

        stage('Build') {
            steps {
                // Build the project using Maven wrapper
                sh './mvnw clean install'
            }
        }

        stage('Test') {
            steps {
                // Run tests using Maven wrapper
                sh './mvnw test'
            }
        }
    }
}
