FROM openjdk:11
WORKDIR /app
COPY target/accounting-0.0.1-SNAPSHOT.jar accounting-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "accounting-0.0.1-SNAPSHOT.jar"]