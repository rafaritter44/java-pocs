FROM eclipse-temurin:25 AS build
WORKDIR /build
COPY app gradle gradle.properties gradlew settings.gradle.kts .
RUN ./gradlew build

FROM eclipse-temurin:25
COPY --from=build /build/app/build/libs/app.jar /opt/app
ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]
