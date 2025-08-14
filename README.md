# SOA Microservices Project

*Last Updated: August 14, 2025*

This project is a microservices-based architecture built using **Spring Boot** and **Spring Cloud**. It implements a social networking platform with user management, messaging, and social engagement features.

## Project Overview

This social networking platform allows users to:
- Create and manage user accounts and profiles
- Connect with other users through friend requests
- Exchange direct messages with other users
- Create posts, comment on posts, and like content
- Discover other users and their content

The application is designed with scalability, maintainability, and resilience in mind, following microservices best practices.

## Architecture Overview

The application follows a microservices architecture pattern with the following components:

1. **SOA (Eureka Server)**: Acts as the service registry for service discovery.
2. **UserSOA**: Manages user accounts, profiles, and friend relationships.
3. **ChatSOA**: Handles messaging functionality between users.
4. **EngagementSOA**: Manages social engagement features (posts, comments, likes).

### Architecture Diagram

```
┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│                 │     │                 │     │                 │
│  Web Browser/   │     │  Mobile App     │     │  External API   │
│  Client         │     │                 │     │  Clients        │
│                 │     │                 │     │                 │
└────────┬────────┘     └────────┬────────┘     └────────┬────────┘
         │                       │                       │
         └───────────────────────┼───────────────────────┘
                                 │
                                 ▼
┌───────────────────────────────────────────────────────────────┐
│                                                               │
│                    Eureka Server (SOA)                        │
│                    Service Registry                           │
│                                                               │
└───────────────────────────────────────────────────────────────┘
          │                  │                  │
          ▼                  ▼                  ▼
┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐
│                 │  │                 │  │                 │
│    UserSOA      │  │    ChatSOA      │  │  EngagementSOA  │
│    (Port 8081)  │  │    (Port 8082)  │  │    (Port 8083)  │
│                 │  │                 │  │                 │
└────────┬────────┘  └────────┬────────┘  └────────┬────────┘
         │                    │                    │
         ▼                    ▼                    ▼
┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐
│                 │  │                 │  │                 │
│  User Database  │  │  Chat Database  │  │ Engagement DB   │
│                 │  │                 │  │                 │
└─────────────────┘  └─────────────────┘  └─────────────────┘
```

### Communication Flow

1. Clients (web browsers, mobile apps, external APIs) interact with the system
2. Each microservice registers with the Eureka Server
3. Services discover each other through Eureka for inter-service communication
4. Each service maintains its own database for domain-specific data
5. Services communicate via REST APIs using RestTemplate

## Project Structure

```
SOA/
├── EurekaSOA/                           # Eureka Server for service discovery
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/              # Java source files
│   │   │   └── resources/         # Configuration files
│   │   └── test/
│   │       └── java/              # Test files
│   ├── Dockerfile
│   └── build.gradle.kts
│
├── UserSOA/                       # User management service
│   └── UserSOA/                   # Actual service implementation
│       ├── src/
│       │   ├── main/
│       │   │   └── java/tn/ensit/soa/UserSOA/
│       │   │       ├── user/                  # User management
│       │   │       ├── profile/               # User profiles
│       │   │       ├── friendrequest/         # Friend relationships
│       │   │       └── UserSoaApplication.java
│       │   └── test/                          # Test files
│       ├── Dockerfile
│       └── build.gradle.kts
│
├── chatSOA/                       # Messaging service
│   └── chatSOA/                   # Actual service implementation
│       ├── src/
│       │   ├── main/
│       │   │   └── java/tn/ensit/soa/chatSOA/
│       │   │       ├── message/               # Message handling
│       │   │       └── ChatSoaApplication.java
│       │   └── test/                          # Test files
│       ├── Dockerfile
│       └── build.gradle.kts
│
├── engagementSOA/                 # Social engagement service
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/tn/ensit/soa/engagementSOA/
│   │   │   │   ├── post/                  # Post management
│   │   │   │   ├── comment/               # Comment functionality
│   │   │   │   ├── like/                  # Like functionality
│   │   │   │   └── EngagementSoaApplication.java
│   │   │   └── resources/                 # Configuration files
│   │   └── test/
│   │       └── java/                      # Test files
│   ├── Dockerfile
│   └── build.gradle.kts
│
├── helm_charts/                   # Helm charts for Kubernetes deployment
│   ├── chat-soa/                  # Helm chart for ChatSOA service
│   │   └── templates/             # Helm templates
│   ├── engagement-soa/            # Helm chart for EngagementSOA service
│   │   └── templates/             # Helm templates
│   ├── eureka-server/             # Helm chart for Eureka Server
│   │   └── templates/             # Helm templates
│   └── user-soa/                  # Helm chart for UserSOA service
│       └── templates/             # Helm templates
│
├── k8s/                           # Kubernetes configuration files
│   ├── infrastructure/            # Infrastructure configurations
│   │   ├── pvc/                   # Persistent Volume Claims
│   │   └── rbac/                  # Role-Based Access Control
│   ├── tools/                     # DevOps tools configurations
│   │   └── sonarqube/             # SonarQube for code quality analysis
│   ├── namespace.yaml
│   └── networkpolicy.yaml
│
├── Jenkins_pipelines/             # Jenkins CI/CD pipeline configurations
│   ├── build/                     # Build pipelines for each service
│   │   ├── SOA_eureka/            # Eureka Server build pipeline
│   │   ├── UserSOA/               # UserSOA build pipeline
│   │   └── chatSOA/               # ChatSOA build pipeline
│   ├── deploy/                    # Deployment pipelines for each service
│   │   ├── SOA_eureka/            # Eureka Server deployment pipeline
│   │   ├── UserSOA/               # UserSOA deployment pipeline
│   │   ├── chatSOA/               # ChatSOA deployment pipeline
│   │   └── engagementSOA/         # EngagementSOA deployment pipeline
│   └── jenkins-shared-lib/        # Shared Jenkins pipeline libraries
│       └── vars/                  # Reusable pipeline functions
│
├── Jenkins_As_Code/               # Jenkins configuration as code
│   ├── config/                    # Jenkins configuration files
│   ├── docker/                    # Docker configurations for Jenkins
│   ├── helm/                      # Helm chart for Jenkins
│   │   └── templates/             # Helm templates
│   ├── k8s/                       # Kubernetes configurations for Jenkins
│   │   ├── pod_templates/         # Pod templates for Jenkins agents
│   │   ├── pvc/                   # Persistent Volume Claims
│   │   └── rbac/                  # Role-Based Access Control
│   └── scripts/                   # Utility scripts
│
├── build.sh                       # Build script
├── docker-compose.yml             # Docker Compose configuration
├── INSTALLATION.md                # Installation guide
└── README.md                      # Project documentation
```

## Service Details

### SOA (Eureka Server)
- **Port**: 8761
- **Purpose**: Service registry for service discovery
- **Application Name**: eureka-server
- **Configuration**:
   - Does not register itself with Eureka (`eureka.client.register-with-eureka=false`)
   - Fetches registry from itself (`eureka.client.fetch-registry=true`)
- **Features**:
   - Registration of microservices
   - Service discovery for inter-service communication
   - Health monitoring of registered services
   - Load balancing support

### UserSOA
- **Port**: 8081
- **Purpose**: User management service
- **Application Name**: UserSOA
- **Features**:
   - User registration and authentication
   - Profile management (creation, updating, retrieval)
   - Friend request handling (sending, accepting, rejecting)
   - User search functionality
- **Data Model**:
   - User: Basic user account information
   - Profile: Extended user profile details
   - FriendRequest: Relationship management between users

### ChatSOA
- **Port**: 8082
- **Purpose**: Messaging service
- **Application Name**: chatSOA
- **Features**:
   - Direct messaging between users
   - Message history retrieval
   - Conversation management
   - Read receipts
- **Data Model**:
   - Message: Contains sender, receiver, content, timestamp

### EngagementSOA
- **Port**: 8083
- **Purpose**: Social engagement service
- **Application Name**: engagementSOA
- **Features**:
   - Post creation, editing, and retrieval
   - Comment management on posts
   - Like functionality for posts and comments
   - Activity feed generation
- **Data Model**:
   - Post: User-generated content with text and metadata
   - Comment: Responses to posts
   - Like: User engagement indicator for posts and comments

## Technologies Used

### Core Technologies
- **Java 17**: Modern Java features including records, enhanced switch expressions, and text blocks
- **Spring Boot 3.x**: Simplified application development with auto-configuration
- **Spring Cloud**: Ecosystem for building cloud-native applications
  - **Spring Cloud Netflix Eureka**: Service registry for service discovery
  - **Spring Cloud LoadBalancer**: Client-side load balancing
- **Spring Data JPA**: Simplified data access layer with repository pattern
- **Spring Web**: RESTful API development with controllers and request mappings
- **Gradle**: Dependency management and build automation

### Data Storage
- **H2 Database**: In-memory database for development and testing
- **JPA/Hibernate**: Object-relational mapping for database interactions

### DevOps & Deployment
- **Docker**: Containerization of microservices
- **Kubernetes**: Container orchestration for production deployment
- **Helm**: Package manager for Kubernetes applications
- **Jenkins**: Continuous Integration and Continuous Deployment

### Communication
- **RestTemplate**: Synchronous HTTP client for inter-service communication
- **JSON**: Data interchange format between services and clients

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
   docker-compose up -d
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

### Jenkins Pipelines (Jenkins_pipelines/)

- **Build Pipelines**: Compile, test, and build Docker images
- **Deploy Pipelines**: Deploy services to Kubernetes environment
- **Shared Libraries**: Reusable pipeline components

The Jenkins pipelines automate:
- Code compilation and testing
- Docker image building and pushing with caching
- Artifact preservation
- Kubernetes deployment using Helm charts

### Pipeline Structure

Each service has its own Jenkinsfile with the following stages:
1. **Checkout**: Shallow clone of the repository for improved performance
2. **Unit Tests**: Run tests in parallel with conditional execution based on file changes
3. **Build**: Compile the application with Gradle caching for faster builds
4. **Docker Login**: Authenticate with Docker registry
5. **Build and Push Image**: Create and push Docker images with layer caching
6. **Save Artifacts**: Preserve build artifacts for future reference
7. **Cleanup**: Remove unused Docker resources

### Kubernetes Integration

The pipelines run on Kubernetes pods with:
- Service-specific pod templates
- Dedicated service accounts with appropriate permissions
- Persistent volume claims for caching and artifacts
- Multiple containers for different build tasks (Docker, Gradle)

### Shared Libraries

The project uses Jenkins shared libraries to promote code reuse:
- `checkoutCode`: Standardized Git checkout
- `gradleBuild`: Gradle build with caching
- `gradleTest`: Test execution with parallel processing
- `dockerLogin`: Secure Docker registry authentication
- `pushDockerImage`: Optimized image building with layer caching
- `saveArtifacts`: Consistent artifact preservation
- `cleanupDocker`: Resource cleanup

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

## Security Considerations

The current implementation focuses on functionality, but for production deployment, consider implementing:

### Authentication and Authorization
- **Spring Security**: Implement OAuth2/JWT-based authentication
- **Role-Based Access Control**: Implement fine-grained permissions for different user types

### Data Protection
- **HTTPS**: Enable TLS/SSL for all service communications
- **Data Encryption**: Encrypt sensitive data at rest and in transit
- **Input Validation**: Implement thorough validation for all user inputs

### Infrastructure Security
- **Network Policies**: Restrict communication between services (already included in k8s configs)
- **Container Security**: Use non-root users in containers (implemented in EngagementSOA)
- **Secret Management**: Use Kubernetes Secrets or external vault solutions for credentials

## Contributing
1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request

## Version History

- **1.0.3** (2025-08-14): Project Structure Documentation Update
  - Updated Project Structure section to accurately reflect current repository organization
  - Fixed formatting issues in the directory tree representation
  - Added missing directories and improved structure clarity

- **1.0.2** (2025-08-14): Documentation update
  - Updated README with current information
  - Improved documentation clarity
  - Added latest project details



