FROM openjdk:8-jdk-alpine
ENV profile default
RUN apk add --no-cache tzdata
ENV TZ Asia/Seoul
COPY applications/app-demo/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${profile}", "app.jar"]
