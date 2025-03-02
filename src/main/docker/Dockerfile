FROM openjdk:21
LABEL authors="egorm"

VOLUME /tmp
WORKDIR /app
ADD maven/avanzaNaturaldentityService-0.0.1-SNAPSHOT.jar /app/identityService.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "identityService.jar"]