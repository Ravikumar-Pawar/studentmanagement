pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                script {
                    echo 'Checking out the code from GitHub...'
                }
                // Checkout code from GitHub using token authentication and specify the branch
                git branch: 'main', credentialsId: 'github-login', url: 'https://github.com/Ravikumar-Pawar/studentmanagement.git'
            }
        }

        stage('Build') {
            steps {
                script {
                    echo 'Building the project...'
                }
                // Build the project using Maven wrapper
                sh './mvnw clean install'
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}
