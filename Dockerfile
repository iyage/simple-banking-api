#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY . .
RUN mvn clean package



FROM openjdk:19

COPY --from=build target/spring-boot-jwt-0.0.1-SNAPSHOT.jar hotel-api.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","hotel-api.jar"]
