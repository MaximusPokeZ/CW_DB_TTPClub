FROM openjdk:17-jdk-slim

LABEL authors="maximuspokez"

WORKDIR /app

COPY target/ttpclub-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]