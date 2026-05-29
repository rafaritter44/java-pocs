FROM eclipse-temurin:25 AS build
WORKDIR /build
COPY app app
COPY gradle gradle
COPY gradle.properties gradlew settings.gradle.kts .
RUN ./gradlew build

FROM eclipse-temurin:25
COPY --from=build /build/app/build/libs/app.jar /opt/app/
ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]
