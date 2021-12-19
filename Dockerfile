FROM openjdk:11 AS build

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package  -DskipTests=true

FROM openjdk:11
WORKDIR /app
COPY --from=build target/accounting-0.0.1-SNAPSHOT.jar accounting-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "accounting-0.0.1-SNAPSHOT.jar"]