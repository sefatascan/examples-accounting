## Table of content

- [Overview](#overview)
- [Environment Requirements](#environment-requirements)
- [Installation](#installation)
    - [docker](#docker)
    - [maven](#maven)
- [Demo](#demo)
    - [Swagger UI](#swagger-ui)
    - [Postman Collection](#postman-collection)
    - [CURL](#curl)

## Overview

This is a spring-boot application. It was developed for personal development purposes. It contains features such as
validation, pageable, dockerize, openapi doc, spring data, exception handling, lombok, auditing.

## Environment Requirements

There are several requirements before startup the application. You can startup with different methods, we cover it in
the  [Installation](#installation) step.

* [docker](#docker) step, you need to have `docker` on your PC.
  see  [docker installation](https://docs.docker.com/engine/install/)

* [maven](#maven) step, you need to have `java` on your PC.

## Installation

Before installation, you need to clone this repository.

```
$ git clone https://github.com/sefatascan/examples-accounting.git
$ cd accounting
```

### docker

In this step, you can run the application with only the `docker` requirement. It may take some time if the _images_ it
needs are not available.

Before running the command, you should check the available of the ports on your PC.

* `:8080` application port
* `:5435` database port needed by the application (if you need connect to db in localhost)

If this port is not available, You need to modify the `docker-compose.yml` file.

```yml
    ports: # application
      - 8080:8080 => [changeable port]:8080
    ports: # database
      - 5435:5432 => [changeable port]:5432
```

After all checks are provided you can run the command.

```
$ docker-compose up -d
```

### maven

In this step, you need `java` and set it as environment variable. Also you need `PostgreSQL`. You should check server
port

```yml
  datasource:
    url: jdbc:postgresql://localhost:5435/accounting
```

* If you want to package and run the application.
  ```
  $ ./mvnv package -DskipTests=true
  $ java -jar target/accounting-0.0.1-SNAPSHOT.jar
  ```

* Or, you can do this with the spring-boot plugin.
  ```
  $ ./mvnv spring-boot::run
  ```

## Demo

There are several methods to test the application.

### Swagger UI

OpenApi support is provided within the application. After the application is running, you can test the APIs with the
relevant URL.

* http://localhost:8080/swagger-ui-custom.html

### Postman Collection

Prepared a collection for you to test with Postman. Just import the `Accounting.postman_collection.json` file with
postman.

### CURL

<details>
  <summary>[POST] /v1/specialist</summary>
  
  ```bash
  curl -X 'POST' \
    'http://localhost:8080/v1/specialist' \
    -H 'accept: application/json' \
    -H 'Content-Type: application/json' \
    -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@gmail.com"
  }'
  ```
</details>

<details>
  <summary>[POST] /v1/transaction</summary>

  ```bash
  curl -X 'POST' \
    'http://localhost:8080/v1/transaction' \
    -H 'accept: */*' \
    -H 'Content-Type: application/json' \
    -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@gmail.com",
    "amount": 150,
    "productName": "TSD",
    "billNo": "TR010"
  }'
  ```
</details>

<details>
  <summary>[GET] /v1/transaction/successuf</summary>

  ```bash
  curl -X 'GET' \
    'http://localhost:8080/v1/transaction/successful?size=5&page=0' \
    -H 'accept: */*'
  ```
</details>

<details>
  <summary>[GET] /v1/transaction/failed</summary>

  ```bash
  curl -X 'GET' \
    'http://localhost:8080/v1/transaction/failed?size=5&page=0' \
    -H 'accept: */*'
  ```
</details>


