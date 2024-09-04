FROM gradle:8.10.0-jdk8 AS build
WORKDIR /app
COPY build.gradle settings.gradle .
COPY src ./src
RUN gradle clean build -x test

FROM eclipse-temurin:8-jdk-alpine
WORKDIR /app
RUN addgroup -S newstest && adduser -S newstest -G newstest
USER newstest:newstest
COPY --from=build /app/build/libs/news-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "news-0.0.1-SNAPSHOT.jar"]