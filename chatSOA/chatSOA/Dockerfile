FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
EXPOSE 8082
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
