FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
EXPOSE 8082
COPY chatSOA/chatSOA/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]