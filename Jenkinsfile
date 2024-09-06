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
                git branch: 'main', credentialsId: 'github-login', url: 'https://github.com/Ravikumar-Pawar/studentmanagement.git'
            }
        }

        stage('Build') {
            steps {
                script {
                    echo 'Building the project...'
                }
                sh './mvnw clean install'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    echo 'Building Docker image...'
                }
                sh 'docker build -t studentmanagement:latest .'
            }
        }

        stage('Deploy with Docker Swarm') {
            steps {
                script {
                    echo 'Deploying with Docker Swarm...'

                    // Define Docker Compose file path
                    def composeFile = 'docker-compose.yml'

                    // Remove existing stack services
                    echo 'Removing existing stack services...'
                    sh '''
                        docker stack rm studentmanagement || true
                    '''

                    // Ensure network is removed if it exists
                    echo 'Ensuring any existing network is removed...'
                    sh '''
                        network_exists=$(docker network ls --filter name=studentmanagement_student-management_app-network -q)
                        if [ -n "$network_exists" ]; then
                            docker network rm studentmanagement_student-management_app-network
                        fi
                    '''

                    // Create network only if it does not exist
                    echo 'Creating Docker network if not present...'
                    sh '''
                        network_exists=$(docker network ls --filter name=studentmanagement_student-management_app-network -q)
                        if [ -z "$network_exists" ]; then
                            docker network create --driver overlay studentmanagement_student-management_app-network
                        fi
                    '''

                    // Deploy the stack
                    echo 'Deploying stack with Docker Compose...'
                    sh '''
                        docker stack deploy -c ${composeFile} studentmanagement
                    '''

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
