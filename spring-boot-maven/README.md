# Sample Spring Boot application used in a Docker container

## Overview

This sample application is based on a Spring Boot guide that walks you through the process of building a Docker container for running a Spring Boot application:

https://spring.io/guides/gs/spring-boot-docker/


## Build the Project via command line
```shell
# Standard Build
mvn clean install 
```

## Create the sample application
```shell
mvn package && java -jar target/sample-spring-boot-docker-0.1.0.jar 
```

Go to http://localhost:8080 to see your "Greetings from Spring Boot!" message.

## Containerize the application
```shell
docker build --tag jmccarthy/spring-boot-maven .
```

## Run the container
```shell
docker run --publish 8080:8080 jmccarthy/spring-boot-maven
```

Go to http://localhost:8080 to see your "Greetings from Spring Boot!" message.
