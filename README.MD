## Envoy w/ external auth example

### Quick Start

build authz server
```
mvn clean install
```
startup envoy , authz server and a service that is behind envoy
```
docker-compose build && docker-compose up -d
```
run test scripts
```
test.sh
```

Headers:
Authorization: Bearer foo  --> allowed
other tokens are forbidden
