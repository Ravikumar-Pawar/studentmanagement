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

        stage('Build Docker Image') {
            steps {
                script {
                    echo 'Building Docker image...'
                }
                // Build Docker image from the Dockerfile
                sh 'docker build -t studentmanagement:latest .'
            }
        }

        stage('Deploy') {
            steps {
                script {
                    echo 'Deploying and running Docker container...'

                    // Define Docker image name and container name
                    def imageName = 'studentmanagement:latest'
                    def containerName = 'studentmanagement-container'

                    // Stop and remove any existing container
                    echo 'Stopping and removing any existing container...'
                    sh """
                        docker stop ${containerName} || true
                        docker rm ${containerName} || true
                    """

                    // Remove any existing Docker image to avoid conflicts (optional)
                    echo 'Removing any existing Docker image...'
                    sh """
                        docker rmi ${imageName} || true
                    """

                    // Run the Docker container
                    echo 'Running Docker container...'
                    sh """
                        docker run -d --name ${containerName} -p 8081:8081 ${imageName}
                    """

                    // Confirm the container is running
                    echo 'Confirming the Docker container is running...'
                    sh '''
                        sleep 10  # Give some time for the container to start
                        if docker ps | grep -q studentmanagement-container; then
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
