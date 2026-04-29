# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# source code
COPY src ./src

# build jar
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]