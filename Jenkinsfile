pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Cloning the GitHub repository
                git 'https://github.com/Ravikumar-Pawar/studentmanagement.git'
            }
        }

        stage('Build') {
            steps {
                // Using Maven to clean, compile, and package the application
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                // Running unit tests with Maven
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                // Packaging the application (generating the JAR file)
                sh 'mvn package'
            }
        }

        stage('Archive Artifacts') {
            steps {
                // Archiving the built JAR file
                archiveArtifacts artifacts: 'target/*.jar', allowEmptyArchive: true
            }
        }
    }

    post {
        success {
            echo 'Build, test, and package successful!'
        }
        failure {
            echo 'Build, test, or package failed.'
        }
    }
}
