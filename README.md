# spring-cloud-config-server

Running at [http://localhost:8888/configserver](http://localhost:8888/configserver).

## Releases

### Next Release `1.0.1-RELEASE`

- Upgrade to ejw-parent:1.4.2-RELEASE

### Current Release `1.0.0-RELEASE`

- Initial release. 

## Build Docker Image

```shell
mvn clean package

mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

docker build -t octopusthu/configserver .
```
