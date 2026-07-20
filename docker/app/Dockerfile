FROM gradle:8.10-jdk21 AS builder
WORKDIR /build
COPY . .
RUN ./gradlew clean build -Pservers

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder \
     /build/sources/src/StartPoint/jvchat-gradle/libs/StartPoint-1.0-SNAPSHOT.jar \
     jvchat.jar
EXPOSE 4004
ENV SERVER_IP=0.0.0.0
ENV SERVER_PORT=4004
ENV MAX_CONNECTIONS=1000
ENTRYPOINT ["java", "-jar", "jvchat.jar"]