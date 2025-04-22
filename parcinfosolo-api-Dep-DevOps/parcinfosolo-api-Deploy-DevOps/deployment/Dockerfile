# Build stage
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

# Copy only the files needed for dependency resolution
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY src src

# Build the application
RUN mvn package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre-alpine

# Add non-root user
RUN addgroup -S spring && adduser -S spring -G spring

# Install necessary packages
RUN apk add --no-cache curl tzdata

# Set timezone
ENV TZ=Europe/Paris

# Create necessary directories
WORKDIR /app
RUN mkdir -p /app/logs /app/config

# Copy the built artifact from build stage
COPY --from=build /app/target/*.jar app.jar

# Set ownership
RUN chown -R spring:spring /app

# Switch to non-root user
USER spring

# Environment variables
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseG1GC"
ENV SPRING_PROFILES_ACTIVE=prod

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"] 