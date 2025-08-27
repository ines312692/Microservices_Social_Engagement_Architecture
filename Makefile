# ============================
# SOA Microservices Project
# Makefile
# ============================

# Services
SERVICES = SOA UserSOA/UserSOA ChatSOA/chatSOA EngagementSOA
IMAGES = eureka-server user-soa chat-soa engagement-soa

# Docker Compose file
COMPOSE_FILE = docker-compose.yml

# ============================
# Gradle Builds
# ============================

.PHONY: build
build: ## Build all services with Gradle
	@for service in $(SERVICES); do \
		echo ">>> Building $$service..."; \
		(cd $$service && ./gradlew clean build -x test); \
	done

.PHONY: test
test: ## Run tests for all services
	@for service in $(SERVICES); do \
		echo ">>> Testing $$service..."; \
		(cd $$service && ./gradlew test); \
	done

# ============================
# Docker
# ============================

.PHONY: docker-build
docker-build: ## Build Docker images for all services
	@echo ">>> Building Docker images..."
	@cd SOA && docker build -t eureka-server .
	@cd UserSOA/UserSOA && docker build -t user-soa .
	@cd ChatSOA/chatSOA && docker build -t chat-soa .
	@cd EngagementSOA && docker build -t engagement-soa .

.PHONY: docker-run
docker-run: ## Run services using Docker
	@echo ">>> Starting containers..."
	@docker run -d -p 8761:8761 --name eureka-server eureka-server
	@docker run -d -p 8081:8081 --name user-soa user-soa
	@docker run -d -p 8082:8082 --name chat-soa chat-soa
	@docker run -d -p 8083:8083 --name engagement-soa engagement-soa

.PHONY: docker-stop
docker-stop: ## Stop and remove containers
	@echo ">>> Stopping containers..."
	@docker rm -f eureka-server user-soa chat-soa engagement-soa || true

.PHONY: docker-clean
docker-clean: docker-stop ## Remove Docker images
	@echo ">>> Removing Docker images..."
	@for image in $(IMAGES); do \
		docker rmi -f $$image || true; \
	done

# ============================
# Docker Compose
# ============================

.PHONY: compose-up
compose-up: ## Start services using Docker Compose
	@docker-compose -f $(COMPOSE_FILE) up -d

.PHONY: compose-down
compose-down: ## Stop services using Docker Compose
	@docker-compose -f $(COMPOSE_FILE) down

# ============================
# Kubernetes
# ============================

.PHONY: k8s-apply
k8s-apply: ## Apply Kubernetes manifests
	@kubectl apply -f k8s/namespace.yaml
	@kubectl apply -f k8s/networkpolicy.yaml
	@kubectl apply -f k8s/infrastructure/pvc/
	@kubectl apply -f k8s/infrastructure/rbac/

.PHONY: k8s-delete
k8s-delete: ## Delete Kubernetes manifests
	@kubectl delete -f k8s/ --ignore-not-found

# ============================
# Helm
# ============================

.PHONY: helm-deploy
helm-deploy: ## Deploy services with Helm
	@helm install eureka-server helm_charts/eureka-server || true
	@helm install user-soa helm_charts/user-soa || true
	@helm install chat-soa helm_charts/chat-soa || true
	@helm install engagement-soa helm_charts/engagement-soa || true

.PHONY: helm-uninstall
helm-uninstall: ## Uninstall Helm releases
	@helm uninstall eureka-server user-soa chat-soa engagement-soa || true

# ============================
# Utility
# ============================

.PHONY: clean
clean: docker-clean compose-down k8s-delete helm-uninstall ## Full cleanup
	@echo ">>> Project cleaned!"

.PHONY: help
help: ## Show available commands
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) \
		| awk 'BEGIN {FS = ":.*?## "}; {printf "  \033[36m%-20s\033[0m %s\n", $$1, $$2}'
