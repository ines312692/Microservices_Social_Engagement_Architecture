# SOA Microservices Project

This project is a microservices-based architecture built using **Spring Boot** and **Spring Cloud**. It implements a social networking platform with user management, messaging, and social engagement features.

## Architecture Overview

The application follows a microservices architecture pattern with the following components:

1. **SOA (Eureka Server)**: Acts as the service registry for service discovery.
2. **UserSOA**: Manages user accounts, profiles, and friend relationships.
3. **ChatSOA**: Handles messaging functionality between users.
4. **EngagementSOA**: Manages social engagement features (posts, comments, likes).

![Microservices Architecture](https://via.placeholder.com/800x400?text=SOA+Microservices+Architecture)

## Project Structure

```
SOA/
├── SOA/                           # Eureka Server for service discovery
│   ├── src/
│   ├── Dockerfile
│   └── build.gradle.kts
│
├── UserSOA/                       # User management service
│   ├── src/main/java/tn/ensit/soa/UserSOA/
│   │   ├── user/                  # User management
│   │   ├── profile/               # User profiles
│   │   ├── friendrequest/         # Friend relationships
│   │   └── UserSoaApplication.java
│   ├── Dockerfile
│   └── build.gradle.kts
│
├── ChatSOA/                       # Messaging service
│   ├── src/main/java/tn/ensit/soa/chatSOA/
│   │   ├── message/               # Message handling
│   │   └── ChatSoaApplication.java
│   ├── Dockerfile
│   └── build.gradle.kts
│
├── EngagementSOA/                 # Social engagement service
│   ├── src/main/java/tn/ensit/soa/engagementSOA/
│   │   ├── post/                  # Post management
│   │   ├── comment/               # Comment functionality
│   │   ├── like/                  # Like functionality
│   │   └── EngagementSoaApplication.java
│   ├── Dockerfile
│   └── build.gradle.kts
│
├── helm_charts/                   # Helm charts for Kubernetes deployment
│   ├── chat-soa/                  # Helm chart for ChatSOA service
│   ├── eureka-server/             # Helm chart for Eureka Server
│   └── user-soa/                  # Helm chart for UserSOA service
│
├── k8s/                           # Kubernetes configuration files
│   ├── infrastructure/
│   │   ├── pvc/                   # Persistent Volume Claims
│   │   └── rbac/                  # Role-Based Access Control
│   ├── namespace.yaml
│   └── networkpolicy.yaml
│
├── Jenkins/                       # Jenkins CI/CD pipeline configurations
│   ├── deploy/                    # Deployment pipelines
│   └── build/                     # Build pipelines
│
└── README.md
```

## Service Details

### SOA (Eureka Server)
- **Port**: 8761
- **Purpose**: Service registry for service discovery
- **Features**:
   - Registration of microservices
   - Service discovery for inter-service communication

### UserSOA
- **Port**: 8081
- **Purpose**: User management service
- **Features**:
   - User registration and authentication
   - Profile management
   - Friend request handling

### ChatSOA
- **Port**: 8082
- **Purpose**: Messaging service
- **Features**:
   - Direct messaging between users
   - Message history retrieval

### EngagementSOA
- **Port**: 8083
- **Purpose**: Social engagement service
- **Features**:
   - Post creation and retrieval
   - Comment management
   - Like functionality

## Technologies Used

- **Java 17**
- **Spring Boot 3.x**
- **Spring Cloud Netflix Eureka** for service discovery
- **Spring Data JPA** for data access
- **Spring Web** for RESTful API development
- **Gradle** for dependency management
- **Docker** for containerization
- **RestTemplate** for inter-service communication
- **H2 Database** (in-memory database for development)

## Prerequisites

- **Java 17** or higher
- **Gradle** installed
- **Docker** (optional, for containerized deployment)
- **Spring Boot** compatible IDE (e.g., IntelliJ IDEA)

## Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/ines312692/SOA.git
   cd SOA
   ```

2. Start the Eureka Server first:
   ```bash
   cd SOA
   ./gradlew bootRun
   ```

3. Start the other services in any order:
   ```bash
   # In separate terminals
   cd UserSOA/UserSOA
   ./gradlew bootRun

   cd ChatSOA/chatSOA
   ./gradlew bootRun

   cd EngagementSOA
   ./gradlew bootRun
   ```

4. Access the Eureka Dashboard at http://localhost:8761 to verify all services are registered.

## Docker Deployment

Each service includes a Dockerfile for containerization. The EngagementSOA service has a fully implemented Dockerfile with:
- Multi-stage build for optimized image size
- JVM memory optimization
- Health check configuration
- Security hardening (non-root user)

To build and run the services using Docker:

1. Build the Docker images:
   ```bash
   # Build Eureka Server
   cd SOA
   docker build -t eureka-server .

   # Build UserSOA
   cd UserSOA/UserSOA
   docker build -t user-soa .

   # Build ChatSOA
   cd ChatSOA/chatSOA
   docker build -t chat-soa .

   # Build EngagementSOA
   cd EngagementSOA
   docker build -t engagement-soa .
   ```

2. Run the containers:
   ```bash
   # Run Eureka Server first
   docker run -d -p 8761:8761 --name eureka-server eureka-server

   # Run other services
   docker run -d -p 8081:8081 --name user-soa user-soa
   docker run -d -p 8082:8082 --name chat-soa chat-soa
   docker run -d -p 8083:8083 --name engagement-soa engagement-soa
   ```

3. Alternatively, you can use Docker Compose (create a docker-compose.yml file in the root directory):
   ```yaml
   version: '3'
   services:
     eureka-server:
       build: ./SOA
       ports:
         - "8761:8761"
     user-soa:
       build: ./UserSOA/UserSOA
       ports:
         - "8081:8081"
       depends_on:
         - eureka-server
     chat-soa:
       build: ./ChatSOA/chatSOA
       ports:
         - "8082:8082"
       depends_on:
         - eureka-server
     engagement-soa:
       build: ./EngagementSOA
       ports:
         - "8083:8083"
       depends_on:
         - eureka-server
   ```

   Then run:
   ```bash
   docker-compose.yml up -d
   ```

## API Endpoints

### UserSOA (Port 8081)
- `POST /users/register` - Register a new user
- `GET /users/{id}` - Get user by ID
- `GET /profiles/{userId}` - Get user profile
- `POST /friend-requests/send` - Send a friend request
- `GET /friend-requests/user/{userId}` - Get friend requests for a user

### ChatSOA (Port 8082)
- `POST /messages/send` - Send a message
- `GET /messages/conversation/{user1Id}/{user2Id}` - Get conversation between two users

### EngagementSOA (Port 8083)
- `POST /posts/create` - Create a new post
- `GET /posts/user/{userId}` - Get all posts by a specific user
- `POST /comments/add` - Add a comment to a post
- `GET /comments/post/{postId}` - Get all comments for a specific post
- `POST /likes/add` - Add a like to a post
- `GET /likes/post/{postId}` - Get all likes for a specific post

## Service Communication

The microservices communicate with each other using REST APIs through RestTemplate. The Eureka Server facilitates service discovery, allowing services to find and communicate with each other without hardcoded URLs.

Example of inter-service communication:
- **EngagementSOA** retrieves user information from **UserSOA** when displaying posts and comments
- **ChatSOA** validates user existence with **UserSOA** before allowing message sending
- **UserSOA** notifies **EngagementSOA** when a user is deleted to clean up associated content

## Kubernetes Deployment

The project includes Kubernetes configuration files for deploying the microservices in a Kubernetes cluster.

### Kubernetes Resources (k8s/)

- **Namespace**: Dedicated namespace for the SOA microservices
- **Network Policies**: Control traffic flow between microservices
- **Infrastructure**:
  - **PVC (Persistent Volume Claims)**: Storage resources for each service
  - **RBAC (Role-Based Access Control)**: Service accounts and permissions

To deploy the application to Kubernetes:

```bash
# Create namespace
kubectl apply -f k8s/namespace.yaml

# Apply network policies
kubectl apply -f k8s/networkpolicy.yaml

# Create infrastructure resources
kubectl apply -f k8s/infrastructure/pvc/
kubectl apply -f k8s/infrastructure/rbac/
```

## Helm Charts

Helm charts are provided for simplified deployment to Kubernetes. Each microservice has its own Helm chart with customizable values.

### Available Charts (helm_charts/)

- **eureka-server**: Helm chart for the Eureka Server
- **user-soa**: Helm chart for the User Service
- **chat-soa**: Helm chart for the Chat Service

To deploy using Helm:

```bash
# Add the repository (if hosted)
helm repo add soa-repo https://your-helm-repo-url

# Install each service
helm install eureka-server helm_charts/eureka-server
helm install user-soa helm_charts/user-soa
helm install chat-soa helm_charts/chat-soa
```

## CI/CD Pipelines

The project includes Jenkins pipeline configurations for continuous integration and deployment.

### Jenkins Pipelines (Jenkins/)

- **Build Pipelines**: Compile, test, and build Docker images
- **Deploy Pipelines**: Deploy services to Kubernetes environment

The Jenkins pipelines automate:
- Code compilation and testing
- Docker image building and pushing
- Kubernetes deployment using Helm charts

## Development

### Adding a New Service
1. Create a new Spring Boot project
2. Add Eureka Client dependency
3. Configure application.properties with a unique name and port
4. Register with Eureka Server by adding `@EnableEurekaClient` annotation
5. Implement REST endpoints for the service's functionality

### Testing
Each service can be tested independently using:
```bash
./gradlew test
```

## Contributing
1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request