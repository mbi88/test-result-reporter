FROM gradle:jdk17-alpine AS builder
ADD . /code
WORKDIR /code
USER root
RUN gradle clean build --no-daemon --console plain

FROM adoptopenjdk:16-jre-hotspot
COPY --from=builder /code/build/libs/test-result-reporter-api-1.0.jar /application/
EXPOSE 8080/tcp
ENTRYPOINT ["java", "-jar", "/application/test-result-reporter-api-1.0.jar"]