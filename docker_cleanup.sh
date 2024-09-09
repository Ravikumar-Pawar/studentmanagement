#!/bin/bash

# Script for Docker cleanup

echo "Starting Docker cleanup..."

# Remove all Docker containers
echo "Stopping and removing all containers..."
sudo docker stop $(sudo docker ps -aq) 2>/dev/null
sudo docker rm $(sudo docker ps -aq) 2>/dev/null

# Ask user if they want to remove unused Docker images
echo "Do you want to remove unused Docker images? (y/n)"
read -r response
if [[ "$response" == "y" || "$response" == "Y" ]]; then
    echo "Removing unused images..."
    sudo docker image prune -a -f
else
    echo "Unused Docker images not removed."
fi

# Remove all Docker volumes
echo "Removing all volumes..."
sudo docker volume rm $(sudo docker volume ls -q) 2>/dev/null

# Remove all Docker networks
echo "Removing all networks..."
sudo docker network rm $(sudo docker network ls -q) 2>/dev/null

# Remove all Docker services
echo "Removing all services..."
sudo docker service rm $(sudo docker service ls -q) 2>/dev/null

# Remove all Docker stacks
echo "Removing all stacks..."
for stack in $(sudo docker stack ls -q); do
  sudo docker stack rm $stack
done

echo "Docker cleanup complete."
