# mod-dcb

Copyright (C) 2022-2023 The Open Library Foundation

This software is distributed under the terms of the Apache License,
Version 2.0. See the file "[LICENSE](LICENSE)" for more information.

## Table of Contents

- [Introduction](#introduction)
- [API information](#api-information)
- [Installing and deployment](#installing-and-deployment)
  - [Compiling](#compiling)
  - [Running it](#running-it)
  - [Docker](#docker)
  - [Module descriptor](#module-descriptor)
  - [Environment variables](#environment-variables)
- [Additional information](#Additional-information)
  - [Issue tracker](#issue-tracker)
  - [API documentation](#api-documentation)
  - [Code analysis](#code-analysis)
  - [Other documentation](#other-documentation)

## Introduction

APIs for managing dcb transactions in folio.

## API information

dcb API provides the following URLs:

| Method | URL                                                     | Permissions                           | Description                                                                         |
|--------|---------------------------------------------------------|---------------------------------------|-------------------------------------------------------------------------------------|
| GET    | /transactions/{dcbTransactionId}/status                      | dcb.transactions.get      | Gets status of transaction based on transactionId                                   |
| POST   | /transactions/{dcbTransactionId}                                             | dcb.transactions.post        | create new transaction                                                              |
| PUT    | /transactions/{dcbTransactionId}/status                            | dcb.transactions.put         | Update the status of the transaction and it will trigger automatic action if needed |
| GET    | /transactions/{dcbTransactionId}/status                            | dcb.transactions.collection.get         | get list of transaction updated between a given query range |

## Installing and deployment

### Compiling

Compile with
```shell
mvn clean install
```

### Running it

Run locally on listening port 8081 (default listening port):

Using Docker to run the local stand-alone instance:

```shell
DB_HOST=localhost DB_PORT=5432 DB_DATABASE=okapi_modules DB_USERNAME=folio_admin DB_PASSWORD=folio_admin \
   java -Dserver.port=8081 -jar target/mod-dcb-*.jar
```

### Docker

Build the docker container with:

```shell
docker build -t dev.folio/mod-dcb .
```

### Module Descriptor

See the built `target/ModuleDescriptor.json` for the interfaces that this module
requires and provides, the permissions, and the additional module metadata.

### Environment variables

| Name                   |    Default value    | Description                                                                                                                                                                |
|:-----------------------|:-------------------:|:---------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| DB_HOST                |      postgres       | Postgres hostname                                                                                                                                                          |
| DB_PORT                |        5432         | Postgres port                                                                                                                                                              |
| DB_USERNAME            |     folio_admin     | Postgres username                                                                                                                                                          |
| DB_PASSWORD            |          -          | Postgres username password                                                                                                                                                 |
| DB_DATABASE            |    okapi_modules    | Postgres database name                                                                                                                                                     |
| KAFKA_HOST             |        kafka        | Kafka broker hostname                                                                                                                                                      |
| KAFKA_PORT             |        9092         | Kafka broker port                                                                                                                                                          |
| KAFKA_SECURITY_PROTOCOL       |       PLAINTEXT       | Kafka security protocol used to communicate with brokers (SSL or PLAINTEXT)                                                                                |
| KAFKA_SSL_KEYSTORE_LOCATION   |           -           | The location of the Kafka key store file. This is optional for client and can be used for two-way authentication for client.                               |
| KAFKA_SSL_KEYSTORE_PASSWORD   |           -           | The store password for the Kafka key store file. This is optional for client and only needed if 'ssl.keystore.location' is configured.                     |
| KAFKA_SSL_TRUSTSTORE_LOCATION |           -           | The location of the Kafka trust store file.                                                                                                                |
| KAFKA_SSL_TRUSTSTORE_PASSWORD |           -           | The password for the Kafka trust store file. If a password is not set, trust store file configured will still be used, but integrity checking is disabled. |
| ENV                    |        folio        | Environment. Logical name of the deployment, must be set if Kafka/Elasticsearch are shared for environments, `a-z (any case)`, `0-9`, `-`, `_` symbols only allowed        |
| SYSTEM\_USER\_NAME     |   dcb-system-user   | Username of the system user                                                                                                                                                |
| SYSTEM\_USER\_PASSWORD |          -          | Password of the system user                                                                                                                                                |
| ACTUATOR\_EXPOSURE     | health,info,loggers | Back End Module Health Check Protocol                                                                                                                                      |
## Additional information

### System user configuration
The module uses system user to communicate with other modules.
For production deployments you MUST specify the password for this system user via env variable:
`SYSTEM_USER_PASSWORD=<password>`.

### Issue tracker

See project [MODDCB](https://issues.folio.org/projects/MODDCB)
at the [FOLIO issue tracker](https://dev.folio.org/guidelines/issue-tracker).

### ModuleDescriptor

See the built `target/ModuleDescriptor.json` for the interfaces that this module
requires and provides, the permissions, and the additional module metadata.

### API documentation

This module's [API documentation](https://dev.folio.org/reference/api/#mod-dcb).

### Code analysis

[SonarQube analysis](https://sonarcloud.io/project/overview?id=org.folio:mod-dcb).

## Other documentation

The built artifacts for this module are available.
See [configuration](https://dev.folio.org/download/artifacts) for repository access,
and the [Docker image](https://hub.docker.com/r/folioci/mod-dcb). Look at contribution guidelines [Contributing](https://dev.folio.org/guidelines/contributing).
