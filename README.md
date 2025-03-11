# C2O Mirror-FFID Handler

A Spring Boot REST service to handle Mirror-FFID requests for C2O (Contract-to-Order) system.

## Overview

This service provides REST endpoints to query and process API requests from the C2O_API_REQUEST table, specifically focusing on requests with status code 318 (queued requests).

## Features

- REST API to retrieve queued API requests
- Health check endpoint
- Integration with Oracle database
- Exception handling

## Prerequisites

- Java 17 or higher
- Maven 3.8.x or higher
- Oracle Database

## Configuration

Database connection and other settings can be configured in the `application.properties` file:

```properties
# Update these properties with your Oracle database details
spring.datasource.url=jdbc:oracle:thin:@//localhost:1521/orcl
spring.datasource.username=c2o_user
spring.datasource.password=c2o_password
```

## Building the Application

```bash
mvn clean install
```

## Running the Application

```bash
java -jar target/c2o-mirrorffid-handler-0.0.1-SNAPSHOT.jar
```

Or using Maven:

```bash
mvn spring-boot:run
```

## API Endpoints

### Get Queued Requests

```
GET /c2o-mirrorffid-handler/api/mirror-ffid/queued-requests
```

Returns all queued requests (status 318) that meet the specified criteria from the SQL query.

### Health Check

```
GET /c2o-mirrorffid-handler/api/mirror-ffid/health
```

Verifies that the service is running.

## Actuator Endpoints

Spring Boot Actuator endpoints are available at:

```
/c2o-mirrorffid-handler/actuator/health
/c2o-mirrorffid-handler/actuator/info
/c2o-mirrorffid-handler/actuator/metrics
```
