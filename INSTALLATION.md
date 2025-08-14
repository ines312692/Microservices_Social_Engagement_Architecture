# Installation and Configuration Guide

This guide explains how to install and configure Minikube, kubectl, and Jenkins for the SOA Microservices project.

## Table of Contents
1. [Minikube Installation](#minikube-installation)
2. [kubectl Installation](#kubectl-installation)
3. [Jenkins Installation](#jenkins-installation)
4. [Jenkins Configuration for the Project](#jenkins-configuration-for-the-project)

## Minikube Installation

Minikube is a tool that allows you to run Kubernetes locally. It creates a virtual machine on your computer that runs a single-node Kubernetes cluster.

### Prerequisites
- An installed hypervisor (such as VirtualBox, Hyper-V, Docker)
- 2 CPUs or more
- 2 GB of free memory
- 20 GB of free disk space
- Internet connection

### Installation on Windows

1. **Download Minikube**
    - Visit the [Minikube download page](https://minikube.sigs.k8s.io/docs/start/)
    - Download the Windows installer (.exe)

2. **Installation via PowerShell (administrator)**
   ```powershell
   New-Item -Path 'c:\' -Name 'minikube' -ItemType Directory -Force
   Invoke-WebRequest -OutFile 'c:\minikube\minikube.exe' -Uri 'https://github.com/kubernetes/minikube/releases/latest/download/minikube-windows-amd64.exe' -UseBasicParsing
   ```

3. **Add to PATH**
   ```powershell
   $oldPath = [Environment]::GetEnvironmentVariable('Path', [EnvironmentVariableTarget]::Machine)
   if ($oldPath.Split(';') -inotcontains 'C:\minikube') { 
     [Environment]::SetEnvironmentVariable('Path', $('{0};C:\minikube' -f $oldPath), [EnvironmentVariableTarget]::Machine) 
   }
   ```

4. **Start Minikube**
   ```powershell
   minikube start --driver=<driver_name>
   ```
   Replace `<driver_name>` with your preferred hypervisor (virtualbox, hyperv, docker).

5. **Verify Installation**
   ```powershell
   minikube status
   ```

## kubectl Installation

kubectl is the command-line tool for interacting with Kubernetes clusters.

### Installation on Windows

1. **Download kubectl**
   ```powershell
   Invoke-WebRequest -OutFile 'C:\minikube\kubectl.exe' -Uri 'https://dl.k8s.io/release/v1.28.0/bin/windows/amd64/kubectl.exe'
   ```

2. **Verify Installation**
   ```powershell
   kubectl version --client
   ```

3. **Configure kubectl to use Minikube**
   ```powershell
   minikube kubectl -- get pods -A
   ```

4. **Create an alias (optional)**
   ```powershell
   New-Alias -Name kubectl -Value minikube kubectl --
   ```

## Jenkins Installation

Jenkins is an open-source automation server that allows you to build, test, and deploy software.

### Installation on Windows

1. **Prerequisites**
    - Java 11 or higher installed
    - Download and install [Java JDK](https://www.oracle.com/java/technologies/downloads/) if necessary

2. **Download Jenkins**
    - Visit [jenkins.io](https://www.jenkins.io/download/)
    - Download the Windows installer (.msi)
    - You can use the jenkins/jenkins:lts image if you prefer to use Docker

3. **Installation**
    - The default installation places Jenkins at http://localhost:8080/

4. **Initial Configuration**
    - Open a browser and go to http://localhost:8080/
    - Retrieve the initial password from the file indicated on the screen
   ```powershell
   Get-Content "C:\Program Files\Jenkins\secrets\initialAdminPassword"
   ```
    - Install the suggested plugins
    - Create an administrator account
    - Configure the Jenkins URL

## Jenkins Configuration for the Project

This section explains how to configure Jenkins for the SOA Microservices project.

### Required Plugins Configuration

1. **Plugin Installation**
    - Go to "Manage Jenkins" > "Manage Plugins" > "Available"
    - Search for and install the following plugins:
        - Kubernetes
        - Docker Pipeline
        - Git Integration
        - Pipeline
        - SonarQube Scanner
        - Credentials Binding

2. **Restart Jenkins**
    - Check "Restart Jenkins when installation is complete and no jobs are running"

### Credentials Configuration

1. **Add Docker Hub Credentials**
    - Go to "Manage Jenkins" > "Manage Credentials" > "Jenkins" > "Global credentials" > "Add Credentials"
    - Select "Username with password"
    - ID: `dockerhub-creds`
    - Username: Your Docker Hub username
    - Password: Your Docker Hub password
    - Description: "Docker Hub Credentials"

2. **Add GitHub Credentials**
    - Go to "Manage Jenkins" > "Manage Credentials" > "Jenkins" > "Global credentials" > "Add Credentials"
    - Select "Username with password"
    - ID: `github-creds`
    - Username: Your GitHub username
    - Password: Your GitHub personal access token
    - Description: "GitHub Credentials"

### SonarQube Configuration

1. **SonarQube Server Configuration**
    - Go to "Manage Jenkins" > "Configure System"
    - Find the "SonarQube servers" section
    - Click on "Add SonarQube"
    - Name: `sonarqube-local`
    - Server URL: `http://sonarqube-service.soa-microservices.svc.cluster.local:9000` (or your SonarQube server URL)
    - Server authentication token: Add your SonarQube token

### Kubernetes Cloud Configuration

1. **Kubernetes Cloud Configuration**
    - Go to "Manage Jenkins" > "Configure System"
    - Find the "Cloud" section
    - Click on "Add a new cloud" > "Kubernetes"
    - Name: `kubernetes`
    - Kubernetes URL: `https://kubernetes.default.svc.cluster.local` (if Jenkins is in the cluster) or use `kubectl cluster-info` to get the URL
    - Kubernetes Namespace: `soa-microservices`
    - Credentials: Select or create credentials to access Kubernetes

2. **Pod Templates Configuration**
    - In the Kubernetes cloud configuration, add a Pod Template:
        - Name: `build-chat`
        - Namespace: `soa-microservices`
        - Labels: `build-chat`
        - Containers:
            - Name: `gradle`
            - Docker image: `gradle:8.6-jdk17`
            - Working directory: `/home/jenkins/agent`
        - Volumes:
            - Host Path Volume:
                - Host path: `/mnt/gradle_cache`
                - Mount path: `/mnt/gradle_cache`
            - Host Path Volume:
                - Host path: `/mnt/artifacts`
                - Mount path: `/mnt/artifacts`

### Pipeline Creation

1. **Create a Pipeline for chatSOA**
    - Go to "New Item"
    - Enter a name (e.g., "chatSOA-build")
    - Select "Pipeline"
    - Click "OK"
    - In the "Pipeline" section:
        - Definition: "Pipeline script from SCM"
        - SCM: Git
        - Repository URL: `https://github.com/ines312692/Microservices_Social_Engagement_Architecture.git`
        - Credentials: Select your GitHub credentials
        - Branch Specifier: `*/feature/restructure-pipeline`
        - Script Path: `Jenkins_pipelines/build/chatSOA/Jenkinsfile`
    - Click "Save"

2. **Create Other Pipelines**
    - Repeat the process for other services (UserSOA, EngagementSOA, SOA_eureka)
    - Use the appropriate Jenkinsfile paths for each service

## Project Execution

Once Minikube, kubectl, and Jenkins are installed and configured, you can run the project:

1. **Start Minikube**
   ```powershell
   minikube start
   ```

2. **Create the namespace**
   ```powershell
   kubectl apply -f k8s/namespace.yaml
   ```

3. **Deploy the infrastructure**
   ```powershell
   kubectl apply -f k8s/infrastructure/pvc/
   kubectl apply -f k8s/infrastructure/rbac/
   ```

4. **Run Jenkins pipelines**
    - Access Jenkins (http://localhost:8080/)
    - Launch the build pipelines for each service

5. **Verify deployments**
   ```powershell
   kubectl get pods -n soa-microservices
   ```

6. **Access services**
   ```powershell
   minikube service list -n soa-microservices
   ```

## Troubleshooting

### Common Issues with Minikube

1. **Minikube doesn't start**
    - Check that virtualization is enabled in the BIOS
    - Try another driver: `minikube start --driver=virtualbox` or `minikube start --driver=docker`
    - Si vous utilisez Docker, connectez le réseau Minikube à Jenkins: docker network connect minikube jenkins

2. **Memory issues**
    - Increase allocated memory: `minikube start --memory=4096`

### Common Issues with Jenkins

1. **Jenkins doesn't start**
    - Check the logs: `Get-Content "C:\Program Files\Jenkins\jenkins.err.log"`
    - Verify that Java is correctly installed: `java -version`
    - Pour tester la connexion à Minikube depuis Jenkins (via Docker)
      docker exec -it jenkins bash
      curl -k https://minikube:8443/version

2. **Kubernetes connection issues**
    - Check that the kubeconfig file is correctly configured
    - Verify the permissions of the Jenkins service account in Kubernetes
    - Vérifiez que le fichier kubeconfig est bien configuré
    - Vérifiez les permissions du service account Jenkins dans Kubernetes

### Common Issues with Pipelines

1. **Docker build failure**
    - Check that Docker is installed and accessible
    - Verify the Docker Hub credentials

2. **Kubernetes deployment failure**
    - Check RBAC permissions
    - Verify that PVCs are created: `kubectl get pvc -n soa-microservices`
- Pour diagnostiquer pourquoi le pipeline ne démarre pas :

`kubectl get pod -n <nom-du-namespace>`

`kubectl describe pod <nom-du-pod> -n <nom-du-namespace>`

`kubectl logs <nom-du-pod> -n <nom-du-namespace>`

`kubectl exec -it <pod-name> -n soa-microservices -- /bin/bash`

`tail -f /var/log/jenkins/jenkins.log`
   