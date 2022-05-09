FROM openjdk:11
ARG PROFILE
ARG APPLICATION_NAME
ARG APP_VERSION

WORKDIR workspace

CMD ["./gradlew", "applications:$APPLICATION_NAME:clean"]
CMD ["./gradlew", "applications:$APPLICATION_NAME:bootJar"]

COPY applications/$APPLICATION_NAME/build/libs/$APPLICATION_NAME-$APP_VERSION.jar /workspace/app.jar

#RUN apk add --no-cache tzdata
#ENV TZ Asia/Seoul

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=default", "app.jar"]
