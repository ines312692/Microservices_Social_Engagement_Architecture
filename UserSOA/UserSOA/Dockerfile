FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

EXPOSE 8081
COPY build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
