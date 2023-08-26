FROM eclipse-temurin:17-jdk-focal
LABEL authors="SAOM"

EXPOSE 8080
COPY target/*.jar branches-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/branches-0.0.1-SNAPSHOT.jar"]