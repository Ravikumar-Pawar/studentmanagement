# Project Documentation

## start services 
```bash
# To start Docker service
sudo systemctl start docker

# To start Jenkins service
sudo systemctl start jenkins

# To start SonarQube service
sudo systemctl start sonarqube

# To start Artifactory service
sudo systemctl start artifactory


````
### standart ports used
```bash
Jenkins:                8080
Artifactory:            8081
SonarQube:              9000
Student Management App: 8085

```

## Table of Contents

1. [Database Configuration](#database-configuration)
2. [Docker Installation and Configuration](#docker-installation-and-configuration)
3. [Docker Compose Installation and Configuration](#docker-compose-installation-and-configuration)
4. [Common Commands](#common-commands)
5. [Jenkins Pipeline File Structure](#jenkins-pipeline-file-structure)
6. [Troubleshooting Commands](#troubleshooting-commands)

## 1. Database Configuration

### Localhost Configuration

Update the database connection details in your `application.properties` file for local development.

### Docker Container Configuration

Configure the database connection in your Docker Compose file (`docker-compose.yml`).

## 2. Docker Installation and Configuration

### Installation on Linux

Follow the instructions for installing Docker on Linux from the [official Docker documentation](https://docs.docker.com/engine/install/).

### Dockerfile

Refer to the `Dockerfile` in your project root for details on how your Docker image is built.

## 3. Docker Compose Installation and Configuration

### Installation on Linux

Follow the instructions for installing Docker Compose from the [official Docker Compose documentation](https://docs.docker.com/compose/install/).

### Docker Compose File

Refer to the `docker-compose.yml` file in your project root for details on how the services are configured.

## 4. Common Commands

For a list of common Docker and Docker Compose commands, refer to the `Common Commands` section in your documentation.

## 5. Jenkins Pipeline File Structure

Refer to the `Jenkinsfile` in your project root for the pipeline configuration used for CI/CD.

## 6. Troubleshooting Commands

### Docker Troubleshooting Commands

```bash
# Check Running Containers
docker ps

# Check Container Logs
docker logs <container_id>

# Inspect a Container
docker inspect <container_id>

# Check Network Information
docker network ls

# Inspect a Network
docker network inspect <network_id>

# List All Docker Images
docker images

# Remove Unused Docker Resources
docker system prune

# View Container Stats
docker stats

# Restart a Container
docker restart <container_id>

# Execute a Command Inside a Running Container
docker exec -it <container_id> <command>


# Check Running Services
docker-compose ps

# View Logs for All Services
docker-compose logs

# View Logs for a Specific Service
docker-compose logs <service_name>

# Build and Restart Services
docker-compose up --build

# Stop and Remove All Containers
docker-compose down

# Check Docker Compose Configuration
docker-compose config

# Restart Services
docker-compose restart


#remove all Docker images
docker rmi $(docker images -q)

#forcefully remove images
docker rmi -f $(docker images -q)

#remove dangling (untagged) images:
docker image prune

#To remove all unused images (not just dangling ones),
docker image prune -a

#Remove all images, containers, and volumes (more aggressive cleanup):
docker system prune -a --volumes


#leave and re-init swarm
docker swarm leave --force
docker swarm init

#If you scaled down the service earlier, you can restart it by scaling it back up to the desired number of replicas.
docker service scale <service-name>=<replicas>

## for student application
docker service scale studentmanagement_studentmanagement-app=2

## Restarting a Service Gracefully
docker service update --force <service-name>


```
# View Jenkins Logs
docker logs <jenkins_container_id>

# Check Jenkins Job Status
# Navigate to the Jenkins dashboard in your web browser to view job status under "Build History".

# Access Jenkins CLI
java -jar jenkins-cli.jar -s http://<jenkins_url> <command>

# View Jenkins System Information
# Navigate to http://<jenkins_url>/systemInfo to access system information.

# Check Jenkins Node Status
# Navigate to Jenkins dashboard > Manage Jenkins > Manage Nodes and Clouds > Nodes to view node status.

# Force Rebuild a Jenkins Job
# In the Jenkins dashboard, go to the specific job and select "Build Now" to trigger a new build.

# Check Jenkins Configuration Files
# SSH into the Jenkins server and check the configuration files located in the Jenkins home directory (`/var/lib/jenkins` or similar).

# Restart Jenkins Service
sudo systemctl restart jenkins
# Or

sudo service jenkins restart
