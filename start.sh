#!/bin/bash

# Define deployment and log directories
deployDir='/home/rpawar/Desktop/deployment'
logDir="${deployDir}/logs"

# Ensure the log directory exists
mkdir -p "${logDir}"

# Kill any existing processes on port 8081
echo "Finding processes on port 8081..."
lsof -i :8081 || true
echo "Killing processes on port 8081..."
for pid in $(lsof -t -i :8081); do
    echo "Killing PID $pid"
    kill -9 $pid || true
done

# Start the application in the background
echo 'Starting the application in the background...'
nohup java -jar studentmanagement-0.0.1-SNAPSHOT.jar > "${logDir}/app.log" 2>&1 &
echo "Started application with PID $(pgrep -f studentmanagement)"
