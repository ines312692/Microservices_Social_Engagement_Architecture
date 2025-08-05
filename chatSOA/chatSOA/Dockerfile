FROM gradle:8.10.2-jdk17 AS deps
WORKDIR /app
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle/ gradle/
RUN --mount=type=secret,id=gradle-properties \
    cp /run/secrets/gradle-properties /root/.gradle/gradle.properties || true && \
    gradle dependencies --no-daemon

FROM deps AS builder
COPY src/main src/main
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle/ gradle/
RUN gradle build --no-daemon --build-cache --parallel

FROM gcr.io/distroless/java17-debian11
COPY --from=builder /app/build/libs/*.jar /app.jar
EXPOSE 8082
HEALTHCHECK --interval=30s --timeout=3s --retries=3 \
  CMD curl -f http://localhost:8082/actuator/health || exit 1
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "/app.jar"]