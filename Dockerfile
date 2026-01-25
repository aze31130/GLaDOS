FROM docker.io/maven:3.9.9-eclipse-temurin-24 AS builder

WORKDIR /build

COPY pom.xml .
COPY src ./src
RUN mvn package

# ---- Stage 2 ----
FROM docker.io/openjdk:26-ea-slim

WORKDIR /app

RUN apt update && apt install curl -y
COPY --from=builder /build/target/GLaDOS-2.0.0.jar /app/GLaDOS.jar
COPY configs /app/configs
COPY items /app/items

ENTRYPOINT ["java", "-jar", "GLaDOS.jar"]