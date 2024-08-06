# Use the base OpenJDK image
FROM openjdk:21

# Set the working directory
WORKDIR /app

# Copy the JAR file from the target directory to /app/app.jar
# Note: This assumes the JAR file is named app.jar and is in the target directory
COPY target/*.jar app.jar

# Expose the port that the application will run on
EXPOSE 8080

# Command to run the JAR file
CMD ["java", "-jar", "app.jar"]
