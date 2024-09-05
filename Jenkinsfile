pipeline {
    agent any

    stages {
        stage('System Info') {
            steps {
                script {
                    echo 'Gathering system information...'

                    // Check Git version
                    echo 'Checking Git version...'
                    sh 'git --version'

                    // Check Maven version
                    echo 'Checking Maven version...'
                    sh './mvnw -v'

                    // Check Java version
                    echo 'Checking Java version...'
                    sh 'java -version'
                }
            }
        }

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

        stage('Deploy') {
            steps {
                script {
                    echo 'Deploying and running on local system...'

                    // Define the local deployment directory
                    def deployDir = '/home/rpawar/Desktop/deployment'

                    // Ensure the deployment directory exists
                    sh "mkdir -p ${deployDir}"

                    // Copy the built artifacts to the deployment directory
                    sh "cp -r target/* ${deployDir}/"

                    // Change to the deployment directory
                    dir(deployDir) {
                        // Example command to run the application (adjust as needed)
                        // This assumes your application can be started with a jar file. Adjust if using a different type of application.
                        echo 'Starting the application...'
                        sh 'java -jar studentmanagement-0.0.1-SNAPSHOT.jar'
                    }
                }
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
