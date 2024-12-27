FROM azul/zulu-openjdk-alpine:17-latest

#RUN apk add --no-cache msttcorefonts-installer fontconfig
#RUN update-ms-fonts

COPY target/*.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/app.jar"]