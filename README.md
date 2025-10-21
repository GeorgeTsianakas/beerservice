# Beer Service

A simple Beer microservice for a Beer Shop, built with Spring Boot.

![Spring](https://img.shields.io/badge/Framework-Spring-informational?style=flat&logo=spring)
![Java](https://img.shields.io/badge/Java-8-blue)
![Build](https://img.shields.io/badge/Build-Maven-orange)

---

## Overview
This service manages Beer catalog data and exposes a REST API for creating, updating, and querying beers. It integrates with an external Inventory service to optionally return current on‑hand quantities, uses JMS (ActiveMQ Artemis) for messaging, MapStruct for DTO mapping, and Ehcache (JCache) for caching. An in‑memory H2 database is used by default for local development; a MySQL profile is provided.

## Features
- RESTful API to manage beers (CRUD and search with pagination)
- Optional inventory lookup via external Beer Inventory Service
- JPA/Hibernate persistence (H2 in memory by default; MySQL profile available)
- Caching via JCache/Ehcache
- Messaging via Spring JMS (ActiveMQ Artemis)
- Actuator endpoints enabled via Spring Boot Actuator

## Tech Stack
- Java 8
- Spring Boot 2.2.x (Web, Data JPA, Actuator, Cache, JMS Artemis)
- H2 (default) / MySQL (optional profile)
- MapStruct, Lombok
- Ehcache (JCache)
- Maven

## Getting Started
### Prerequisites
- Java 8 (JDK)
- Maven 3.6+
- Optional: Local or reachable Beer Inventory Service (for on‑hand quantities)
- Optional: ActiveMQ Artemis broker (the Spring Boot Artemis starter can auto‑configure an embedded broker for local dev)

### Clone
```
git clone <this-repo-url>
cd beerservice
```

### Build
```
mvn clean package
```

### Run (H2, default)
```
mvn spring-boot:run
```
The application starts on http://localhost:8080.

- H2 console: http://localhost:8080/h2-console (enabled)
- Actuator root: http://localhost:8080/actuator

### Run with MySQL profile
Provide a running MySQL and adjust credentials as needed. A sample config is available at `src/main/resources/application-localmysql.properties`.

```
mvn spring-boot:run -Dspring-boot.run.profiles=localmysql
```

## Configuration
Key properties (set in application.properties, profile files, or environment variables):

- H2 (default):
  - spring.datasource.url=jdbc:h2:mem:testdb;MODE=MYSQL
  - spring.h2.console.enabled=true
- Caching:
  - spring.cache.jcache.config=classpath:ehcache.xml
- JMS Artemis credentials (default/dev):
  - spring.artemis.user=artemis
  - spring.artemis.password=simetraehcapa
- External Inventory Service base URL:
  - sfg.brewery.beer-inventory-service-host=http://localhost:8082  (example)

The Inventory client composes calls like:
GET {sfg.brewery.beer-inventory-service-host}/api/v1/beer/{beerId}/inventory

## API
Base path: /api/v1

### List Beers
GET /api/v1/beer

Query parameters:
- pageNumber (int, default 0)
- pageSize (int, default 25)
- beerName (string, optional)
- beerStyle (enum, optional; see BeerStyleEnum)
- showInventoryOnHand (boolean, default false)

Example:
```
curl "http://localhost:8080/api/v1/beer?pageNumber=0&pageSize=10&beerName=Pale&showInventoryOnHand=true"
```
Returns: BeerPagedList (JSON) with paging metadata and items.

### Get Beer by ID
GET /api/v1/beer/{beerId}

Query parameters:
- showInventoryOnHand (boolean, default false)

Example:
```
curl "http://localhost:8080/api/v1/beer/3fa85f64-5717-4562-b3fc-2c963f66afa6?showInventoryOnHand=true"
```

### Get Beer by UPC
GET /api/v1/beerUpc/{upc}

Example:
```
curl "http://localhost:8080/api/v1/beerUpc/0631234200036"
```

### Create Beer
POST /api/v1/beer

Body (example):
```
{
  "beerName": "Galaxy Cat",
  "beerStyle": "PALE_ALE",
  "upc": "0631234200036",
  "price": 12.99
}
```

### Update Beer
PUT /api/v1/beer/{beerId}

Body: BeerDto (same structure as above)

## Architecture Notes
- Domain model: `com.beerservice.domain.Beer`
- DTOs: `com.brewery.model.*` with `MapStruct` mappers (`BeerMapper` and decorator)
- Services: `BeerService` with implementation `BeerServiceImpl`
- Inventory client: `BeerInventoryServiceRestTemplateImpl` using property `sfg.brewery.beer-inventory-service-host`
- Messaging: Artemis queues/topics configured in `com.beerservice.config.JmsConfig`
- Caching: `CacheConfig` + `ehcache.xml`
- Data bootstrap: `BeerLoader` seeds sample data

## Database
- Default: In‑memory H2 (no setup required)
- Optional: MySQL settings in `application-localmysql.properties` and `src/main/scripts/mysql-init.sql` for initialization

## Development
- Run tests: `mvn test`
- MapStruct is configured with the Spring component model and Lombok
- Pagination is implemented via Spring Data `PageRequest`

## Troubleshooting
- Inventory quantities always 0: ensure `sfg.brewery.beer-inventory-service-host` points to a running inventory service and is reachable.
- Artemis connection issues: verify credentials and broker URL; the starter can run an embedded broker in dev scenarios.
- H2 console: ensure `spring.h2.console.enabled=true` and use JDBC URL from `application.properties`.

## Branches
At the time of this update, only the `master` branch is present in the remote (`origin/master`). No alternative implementation branches were found.

## License
This project is provided as‑is for educational/demo purposes. Use at your own discretion.
