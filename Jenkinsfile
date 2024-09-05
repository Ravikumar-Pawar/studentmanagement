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

                    // Define the local deployment directory and log directory
                    def deployDir = '/home/rpawar/Desktop/deployment'
                    def logDir = "${deployDir}/logs"

                    // Ensure the deployment and log directories exist
                    sh "mkdir -p ${deployDir}"
                    sh "mkdir -p ${logDir}"

                    // Kill any existing processes on port 8081
                    echo 'Checking for and killing any processes running on port 8081...'
                    sh '''
                        echo "Finding processes on port 8081..."
                        lsof -i :8081 || true
                        echo "Killing processes on port 8081..."
                        for pid in $(lsof -t -i :8081); do
                            echo "Killing PID $pid"
                            kill -9 $pid || true
                        done
                    '''

                    // Copy the JAR file and service file to the deployment directory
                    echo 'Copying JAR and service files to deployment directory...'
                    sh "cp target/studentmanagement-0.0.1-SNAPSHOT.jar ${deployDir}/"
                    sh "cp studentmanagement.service ${deployDir}/"

                    // Change to the deployment directory
                    dir(deployDir) {
                        // Verify files are present
                        sh 'ls -l'

                        // Create a symlink for the service file and reload systemd
                        echo 'Setting up the service...'
                        sh '''
                            sudo ln -sf ${PWD}/studentmanagement.service /etc/systemd/system/studentmanagement.service
                            sudo systemctl daemon-reload
                            sudo systemctl enable studentmanagement.service
                        '''

                        // Start the service
                        echo 'Starting the application service...'
                        sh '''
                            sudo systemctl start studentmanagement.service
                        '''

                        // Confirm the application service is running
                        echo 'Confirming the application service is running...'
                        sh '''
                            if sudo systemctl is-active --quiet studentmanagement.service; then
                                echo "Application service is running."
                            else
                                echo "Application service failed to start."
                                sudo journalctl -xe
                                exit 1
                            fi
                        '''
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
