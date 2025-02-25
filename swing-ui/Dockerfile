# Stage 1: Build the application
FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src src

# Ensure the resources (including config.properties) are copied
COPY src/main/resources/config.properties /app/resources/config.properties

# Build the application
RUN mvn clean package

# Stage 2: Create the runtime image based on Debian-based OpenJDK image
FROM openjdk:17-slim

# Install X11 libraries required for Swing GUI
RUN apt-get update && apt-get install -y \
    libxext6 \
    libxrender1 \
    libx11-6 \
    libxtst6 \
    x11-apps \
    && rm -rf /var/lib/apt/lists/*

VOLUME /tmp

# Copy the built JAR file
COPY --from=build /app/target/front-employee-records-management.jar /front-employee-records-management.jar

COPY --from=build /app/resources/config.properties /app/resources/config.properties

ENV DISPLAY=:0
ENV API_BACKEND=http://backend:8080

# Correct the filename in the ENTRYPOINT
ENTRYPOINT ["java", "-jar", "/front-employee-records-management.jar"]
