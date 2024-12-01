FROM openjdk:21-jdk-slim

ARG JAR_FILE=target/fingeapi-0.0.1.jar

COPY ${JAR_FILE} fingeapi.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","fingeapi.jar"]
