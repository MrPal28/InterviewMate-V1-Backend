
# -------- Build stage --------
FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /workspace

# Copy pom.xml and download dependencies first (caching layer)
COPY pom.xml ./
RUN mvn dependency:go-offline

# Copy source code and build the JAR
COPY src/ src/
RUN mvn package -DskipTests -B

# -------- Runtime stage --------
FROM bellsoft/liberica-runtime-container:jre-21-slim-musl
WORKDIR /app

# Create a non-root user for security
RUN addgroup -S appuser && adduser -S -G appuser appuser

# Copy the compiled JAR from the builder
COPY --from=build /workspace/target/notification-service-0.0.1-SNAPSHOT.jar /app/notification-service.jar

# Expose the service port
EXPOSE 8082

# Use the non-root user
USER appuser

# Entrypoint with container-friendly JVM flags
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "/app/notification-service.jar"]
