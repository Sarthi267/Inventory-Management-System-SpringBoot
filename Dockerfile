# Build stage
FROM maven:3-eclipse-temurin-21-alpine AS builder
WORKDIR /app
# Copy pom.xml first to cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B
# Copy source code and build the application executable
COPY src ./src
RUN mvn package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Create a non-root user for security
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
# Copy built JAR file from the builder stage
COPY --from=builder /app/target/*.jar app.jar
# Change ownership to non-root user
USER appuser

EXPOSE 8080
# Run the application with optimized JVM container options
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-jar", "app.jar"]