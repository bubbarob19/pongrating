# Use the official OpenJDK image as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file produced by the Spring Boot build into the container
COPY target/*.jar app.jar

# Expose the port that the Spring Boot application will run on
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]