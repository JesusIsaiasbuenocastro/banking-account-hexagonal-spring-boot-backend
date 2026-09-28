# ---- Etapa de build ----
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Cacheamos las dependencias primero para builds mas rapidos
COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B clean package -DskipTests

# ---- Etapa de ejecucion ----
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
