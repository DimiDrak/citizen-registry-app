# Stage 1: Build the Maven project
FROM maven:3.9.6-eclipse-temurin-21-alpine@sha256:53f16a14a83c0c3600ec8e18d21939996816a085b8a2699b835fedade1ed893a AS build
WORKDIR /app

# Copy only the POM file to leverage Docker caching
COPY pom.xml .

# Copy the spring boot sub-project
RUN mkdir citizen-spring
COPY src /app/citizen-spring/src
COPY pom.xml /app/citizen-spring/pom.xml

# Package secondly the Spring Boot service
WORKDIR /app/citizen-spring
# Download dependencies. This layer will be cached if pom.xml is not changed
RUN mvn -B dependency:go-offline
# Package without tests
RUN mvn -B clean package -DskipTests

# Stage 2: Create a minimal runtime image
FROM eclipse-temurin:21-alpine@sha256:b5d37df8ee5bb964bb340acca83957f9a09291d07768fba1881f6bfc8048e4f5 AS runtime

# Set a non-root user for security reasons
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Create working directory
RUN mkdir /app && chown -R appuser:appgroup /app
WORKDIR /app

# Copy the JAR file built in the previous stage
COPY --from=build --chown=appuser:appuser /app/citizen-spring/target/citizens-1.0.jar .

# Change to non-root user
USER appuser

# Expose the port your application listens on
EXPOSE 8080

#Environment variables
ENV DB_HOST=localhost
ENV DB_NAME=citizens
ENV DB_USER=root
ENV DB_PASSWORD=root

# Command to run the application
ENTRYPOINT ["/opt/java/openjdk/bin/java", "-jar", "/app/citizens-1.0.jar"]