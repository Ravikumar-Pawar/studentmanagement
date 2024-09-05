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

                    // Check Docker version
                    echo 'Checking Docker version...'
                    sh 'docker --version'

                    // Check Docker Compose version
                    echo 'Checking Docker Compose version...'
                    sh 'docker compose version'
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

        stage('Build Docker Image') {
            steps {
                script {
                    echo 'Building Docker image...'
                }
                // Build Docker image from the Dockerfile
                sh 'docker build -t studentmanagement:latest .'
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                script {
                    echo 'Deploying with Docker Compose...'

                    // Define Docker Compose file path
                    def composeFile = 'docker-compose.yml'

                    // Stop and remove any existing container defined in the Docker Compose file
                    echo 'Stopping and removing any existing containers...'
                    sh """
                        docker compose -f ${composeFile} down || true
                    """

                    // Run Docker Compose to start the containers
                    echo 'Starting containers with Docker Compose...'
                    sh """
                        docker compose -f ${composeFile} up -d
                    """

                    // Confirm the application is running
                    echo 'Confirming the application is running...'
                    sh '''
                        sleep 10  # Give some time for the containers to start
                        if docker-compose ps | grep -q studentmanagement; then
                            echo "Docker container is running."
                        else
                            echo "Docker container is not running."
                            exit 1
                        fi
                    '''
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
