FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
COPY build/libs/*.jar app.jar
EXPOSE 8083
USER appuser

ENTRYPOINT ["java", "-Xmx256m", "-Xms128m", "-jar", "/app/app.jar"]
