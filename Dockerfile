# =============================================================
# Multi-Stage Build
#   Stage 1 (builder) : compile source and produce a Fat JAR
#   Stage 2 (runtime) : run the JAR on a lightweight JRE image
# The final image contains no JDK, Maven, or source code.
# =============================================================

# ---- Stage 1: Build ----
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

# Copy dependency descriptors first to leverage Docker layer caching.
# Maven dependencies are re-downloaded only when pom.xml changes.
COPY pom.xml .
COPY .mvn/ .mvn/
COPY mvnw .

RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Copy source and package (skip tests — tests run in CI, not during image build)
COPY src/ src/
RUN ./mvnw package -DskipTests -B

# ---- Stage 2: Runtime ----
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Run as non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy only the Fat JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

# UseContainerSupport: makes JVM respect container CPU/memory limits
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-jar", "app.jar"]
