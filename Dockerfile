FROM openjdk:11-jre-slim
ARG PROFILE=default
ENV profile=${PROFILE}
RUN apt-get update && apt-get install -y fontconfig libfreetype6 && apt-get install -y tzdata
ENV TZ Asia/Seoul
COPY applications/app-demo/build/libs/*.jar app.jar
ENTRYPOINT java -jar -Dspring.profiles.active=$profile app.jar
