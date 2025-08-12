# Jenkins as Code

This directory contains the configuration and deployment files for setting up Jenkins using the "Configuration as Code" approach for the SOA Microservices project.

## Purpose

The Jenkins_As_Code directory provides a complete, version-controlled setup for Jenkins that can be deployed consistently across different environments. It uses Jenkins Configuration as Code (JCasC) to define Jenkins configuration in YAML files rather than through the UI.

## Directory Structure

- `config/`: Contains JCasC configuration files
- `docker/`: Contains Dockerfile and docker-compose for local development
- `helm/`: Contains Helm chart for Kubernetes deployment
- `k8s/`: Contains Kubernetes resource definitions
- `scripts/`: Contains deployment and setup scripts

## Configuration Files

### Config Directory
- `jenkins.yaml`: Main JCasC configuration file defining Jenkins system settings, cloud configuration, and pod templates
- `credentials.yaml`: Defines credentials used by Jenkins (DockerHub, Kubernetes)
- `plugins.yaml`: Lists required Jenkins plugins

### Docker Directory
- `Dockerfile`: Defines the Jenkins container image with necessary tools (Docker CLI, kubectl, Helm)
- `docker-compose.yml`: For local development and testing

### Helm Directory
- `Chart.yaml`: Helm chart metadata
- `values.yaml`: Default values for the Helm chart
- `templates/`: Kubernetes manifest templates for the Helm chart

### Kubernetes Directory
- `k8s/pvc/`: Persistent Volume Claims for Jenkins
- `k8s/rbac/`: RBAC configurations (ServiceAccounts, RoleBindings)
- `k8s/pod_templates/`: Pod templates for Jenkins agents

## Deployment Instructions

### Using Helm

1. Ensure you have Helm installed and configured with your Kubernetes cluster
2. Run the setup script:
   ```
   ./scripts/setup-jenkins.sh
   ```

This will:
- Install the Jenkins Helm chart
- Apply necessary Kubernetes resources (PVCs, RBAC)
- Wait for Jenkins to be ready

## Usage

Once deployed, Jenkins will be configured with:
- Kubernetes cloud configuration for dynamic agent provisioning
- Pod templates for building different microservices
- Necessary credentials for DockerHub and Kubernetes

Access Jenkins through the service endpoint defined in your Kubernetes cluster.

## Customization

To customize the Jenkins configuration:
1. Modify the YAML files in the `config/` directory
2. Update the Helm values in `helm/values.yaml` if needed
3. Redeploy using the setup script