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

        stage('SonarQube Analysis') {
            steps {
                script {
                    echo 'Running SonarQube analysis...'
                }
                withSonarQubeEnv('sonarqube') {
                    sh './mvnw sonar:sonar -Dsonar.projectKey=studentmanagement -Dsonar.host.url=http://localhost:9000 -Dsonar.login=$SONAR_LOGIN'
                }
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

        stage('Push Docker Image to Hub') {
            steps {
                script {
                    echo 'Pushing Docker image to Docker Hub...'

                    // Log in to Docker Hub
                    withCredentials([usernamePassword(credentialsId: 'docker-login', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
                        sh 'echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin'
                    }

                    // Tag and push the image
                    sh 'docker tag studentmanagement:latest ravikumarpawar/studentmanagement:latest'
                    sh 'docker push ravikumarpawar/studentmanagement:latest'
                }
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
                        sleep 10  # Allow time for removal
                    '''

                    // Deploy the stack
                    echo 'Deploying stack with Docker Compose...'
                    sh "docker stack deploy -c ${composeFile} studentmanagement"

                    // Confirm the application is running
                    echo 'Confirming the application is running...'
                    sh '''
                        sleep 30  # Give some time for the containers to start
                        echo "Checking Docker stack status..."
                        docker stack ps studentmanagement
                        docker service ls
                        # Check if all services are in 'Running' state (case-insensitive)
                        if docker stack ps studentmanagement | grep -i "running" > /dev/null; then
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
            echo 'Check if the application is accessible at http://127.0.0.1:8081/students'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}
