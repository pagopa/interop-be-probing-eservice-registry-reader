## BUILD ##
FROM maven:3.8.3-jdk-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn -q package -Dmaven.test.skip=true


## RUN ##
FROM openjdk:17-alpine

COPY --from=build /app/target/*.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]