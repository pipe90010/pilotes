# Use the official OpenJDK image from the Docker Hub
FROM openjdk:17-jdk-slim

# Expose port 8080 to allow external access
EXPOSE 8080

# Make and set the working directory inside the container
RUN mkdir -p /application/

# Copy the JAR file from the target directory of the host to the container
COPY target/pilotes-0.0.1-SNAPSHOT.jar /application/

ENTRYPOINT ["java"]

# Command to run the Spring Boot application
CMD [ "-jar", \
    "/application/pilotes-0.0.1-SNAPSHOT.jar" \
]