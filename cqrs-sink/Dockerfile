#FROM azul/zulu-openjdk-alpine:17-latest
FROM azul/zulu-openjdk-alpine:17.0.11-17.50-x86

#RUN apk add --no-cache msttcorefonts-installer fontconfig
#RUN update-ms-fonts

COPY target/*.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/app.jar"]