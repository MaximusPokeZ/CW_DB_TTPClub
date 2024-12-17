# Используем Maven для сборки .jar
FROM maven:3.8.4-openjdk-17 as builder

WORKDIR /app

# Копируем pom.xml и все исходники для выполнения сборки
COPY pom.xml /app/
COPY src /app/src/

# Собираем проект, пропуская тесты
RUN mvn clean package -Dmaven.test.skip=true

# Используем slim образ для runtime
FROM openjdk:17-jdk-slim

WORKDIR /app

# Копируем только готовый .jar файл из предыдущего этапа
COPY --from=builder /app/target/ttpclub-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
