#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY . .
RUN mvn clean package -Dmaven.test.skip=true



FROM openjdk:19

COPY --from=build target/simpleBankApp-0.0.1-SNAPSHOT.jar bank-api.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","bank-api.jar"]
