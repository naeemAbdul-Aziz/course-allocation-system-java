# Use Eclipse Temurin 17 as base image (more reliable on some systems)
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy Maven wrapper or pom.xml and source
COPY pom.xml .
COPY src ./src

# Install Maven and build the app
RUN apk add --no-cache maven && mvn clean package -DskipTests

# Expose port 8080
EXPOSE 8080

# Run the JAR
CMD ["java", "-jar", "target/course-allocation-server-1.0-SNAPSHOT.jar"]