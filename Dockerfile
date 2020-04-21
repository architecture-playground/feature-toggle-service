FROM gradle:6.3.0-jdk14 as build

COPY . /feature-toggle-service
WORKDIR /feature-toggle-service

RUN echo "Gradle clean build started" && \
    # build jar but don't execute tests since "docker in docker" issue
    gradle clean build -x test && \
    echo "Gradle clean build finished" && \
    mkdir /app && \
    mv ./build/libs/*.jar /app/ && \
    echo "Application jar moved to /app directory"

# Postpone executing tests when container started
CMD gradle test

FROM adoptopenjdk/openjdk13:x86_64-alpine-jdk-13.0.2_8-slim as app
COPY --from=build /app/ .
ENTRYPOINT java -jar /app/*.jar
