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

        stage('Deploy with Docker Swarm') {
            steps {
                script {
                    echo 'Deploying with Docker Swarm...'

                    // Define Docker Compose file path
                    def composeFile = 'docker-compose.yml'

                    // Initialize Docker Swarm if not already initialized
                    echo 'Initializing Docker Swarm...'
                    sh 'docker swarm init || true'

                    // Deploy the stack with Docker Compose
                    echo 'Deploying stack with Docker Compose...'
                    sh """
                        docker stack deploy -c ${composeFile} studentmanagement
                    """

                    // Confirm the application is running
                    echo 'Confirming the application is running...'
                    sh '''
                        sleep 10  # Give some time for the containers to start
                        if docker stack ps studentmanagement | grep -q "running"; then
                            echo "Docker stack is running."
                        else
                            echo "Docker stack is not running."
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
