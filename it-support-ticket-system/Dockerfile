# Stage 1: Build the application
FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src src

# Build the application
RUN mvn clean package

# Stage 2: Create the runtime image
FROM openjdk:17-slim
VOLUME /tmp

# Copy the built JAR file
COPY --from=build /app/target/it-support-ticket-system.jar it-support-ticket-system.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/it-support-ticket-system.jar"]
