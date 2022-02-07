#!/bin/sh

echo "The current working directory $var"
var=$(pwd)

echo "Going to build the project using maven build tool"
mvn clean install

if docker ps -f name=mysql
then
  echo "Going to stop mysql running container"
  docker stop mysql
fi
echo "Going to start MySQL docker image"
docker-compose up -d
echo "Going to start the Stock API service application"
java -jar $var/stock-service/target/stock-service-1.0.0-SNAPSHOT.jar
