FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY EngagementSOA/build/libs/*.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
