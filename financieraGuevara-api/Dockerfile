FROM jelastic/maven:3.9.5-openjdk-21 AS build
COPY /RestaurantBere-api/pom.xml .

FROM openjdk:21-jdk-slim
FROM amazoncorretto:21-alpine-jdk
COPY /financieraGuevara-api/target/financieraGuevara-api-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]