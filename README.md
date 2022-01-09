# spring-cloud-config-server

[![Java CI with Maven](https://github.com/octopusthu/spring-cloud-config-server/actions/workflows/maven.yml/badge.svg)](https://github.com/octopusthu/spring-cloud-config-server/actions/workflows/maven.yml)
[![Publish Docker image](https://github.com/octopusthu/spring-cloud-config-server/actions/workflows/publish-docker-image.yml/badge.svg)](https://github.com/octopusthu/spring-cloud-config-server/actions/workflows/publish-docker-image.yml)

An instance of Spring Cloud Config Server with out-of-the-box functionalities such as HTTP Basic authentication.

## Build Docker Image

```bash
mvn clean package

docker build -t octopusthu/spring-cloud-config-server .
```
