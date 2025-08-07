# Dockerfile (UserSOA)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

EXPOSE 8081

# Copier le jar généré par Jenkins dans l'image
COPY build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
