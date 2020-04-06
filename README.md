Frontend Application Service
---

The service handles requests published to Apache Kafka, fetched customer information from Oxus CRM and publishes response back to Kafka.

# Getting Started

## Build

```
mvn clean package
```

## Dockerize

```
sudo docker build -t risc:v1 .
```

## Start container

```
sudo docker run -d -p 8989:8989 -t risc:v1
```