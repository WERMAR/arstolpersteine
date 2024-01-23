# Use an official OpenJDK runtime as a base image
FROM --platform=$BUILDPLATFORM openjdk:23-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container at /app
COPY target/ /app/

# Expose port 8580
EXPOSE 8580

# Define environment variable
ENV JAVA_OPTS=""

# Command to run the application
CMD ["sh", "-c", "java $JAVA_OPTS -jar arstolpersteine-backend-0.0.1-SNAPSHOT.jar"]
