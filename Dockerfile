FROM amazoncorretto:11-alpine-jdk
MAINTAINER thechitesh@gmail.com
COPY stock-service/target/stock-service-1.0.0-SNAPSHOT.jar stocks-api-1.0.0.jar
EXPOSE 8000
ENTRYPOINT ["java", "-Dspring.profiles.active=docker","-jar","/stocks-api-1.0.0.jar"]