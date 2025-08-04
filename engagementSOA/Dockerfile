# Build stage
FROM gradle:8.4.0-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle build --no-daemon

# Run stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8083
USER appuser
ENTRYPOINT ["java","-Xmx256m","-Xms128m","-jar","/app/app.jar"]
