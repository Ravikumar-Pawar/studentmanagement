# Use the official OpenJDK base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the target directory of the build context to the working directory
COPY target/studentmanagement-0.0.1-SNAPSHOT.jar /app/studentmanagement.jar

# Expose the port that the application will run on
EXPOSE 8081

# Command to run the application
CMD ["java", "-Dserver.port=8081", "-jar", "studentmanagement.jar"]
