FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
EXPOSE 8082
COPY build/libs/chatSOA-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]