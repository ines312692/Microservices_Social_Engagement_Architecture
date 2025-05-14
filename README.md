# SOA Microservices Project

This project is a microservices-based architecture built using **Spring Boot** and **Spring Cloud**. It includes the following services:

1. **engagementSOA**: Manages comments and likes for posts.
2. **chatSOA**: Handles chat or messaging functionality.
3. **userSOA**: Manages user-related operations.
4. **eurekaSOA**: Acts as the service registry using Eureka Server.

## Features

- **Comment Management**: Add and retrieve comments for posts.
- **Like Management**: Add and retrieve likes for posts.
- **Chat Functionality**: Messaging between users.
- **User Management**: User-related operations like registration and profile management.
- **Service Discovery**: Eureka Server for service registration and discovery.

## Technologies Used

- **Java** and **Kotlin**
- **Spring Boot**
- **Spring Cloud Netflix Eureka**
- **Gradle** for dependency management
- **RestTemplate** for inter-service communication

## Prerequisites

- **Java 17** or higher
- **Gradle** installed
- **Spring Boot** compatible IDE (e.g., IntelliJ IDEA)

## Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/ines312692/SOA.git
   cd SOA