# spring-cloud-config-server

Running at [http://localhost:8888/configserver](http://localhost:8888/configserver).

## Releases

### Next Release `1.0.0-SNAPSHOT`

### Current Release

## Build Docker Image

```shell
mvn clean package

mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

docker build -t octopusthu/configserver .
```
