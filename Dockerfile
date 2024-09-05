# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the local machine to the container
COPY target/studentmanagement-0.0.1-SNAPSHOT.jar app.jar

# Run the JAR file with the port set to 8081
ENTRYPOINT ["java", "-Dserver.port=8081", "-jar", "/app/app.jar"]

# Expose port 8081
EXPOSE 8081
