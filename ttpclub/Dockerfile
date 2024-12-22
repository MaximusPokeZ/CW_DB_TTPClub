FROM maven:3.8.4-openjdk-17 as builder

WORKDIR /app

COPY pom.xml /app/
COPY src /app/src/

RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:17-jdk-slim

RUN apt-get update && \
    apt-get install -y wget lsb-release && \
    echo "deb http://apt.postgresql.org/pub/repos/apt/ $(lsb_release -c | awk '{print $2}')-pgdg main" | tee /etc/apt/sources.list.d/pgdg.list && \
    wget --quiet -O - https://www.postgresql.org/media/keys/ACCC4CF8.asc | tee /etc/apt/trusted.gpg.d/pgdg.asc && \
    apt-get update && \
    apt-get install -y postgresql-client-15


WORKDIR /app

COPY --from=builder /app/target/ttpclub-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
