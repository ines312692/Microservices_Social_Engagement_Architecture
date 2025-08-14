# Guide d'Installation et de Configuration

Ce guide explique comment installer et configurer Minikube, kubectl et Jenkins pour le projet SOA Microservices.

## Table des matières
1. [Installation de Minikube](#installation-de-minikube)
2. [Installation de kubectl](#installation-de-kubectl)
3. [Installation de Jenkins](#installation-de-jenkins)
4. [Configuration de Jenkins pour le projet](#configuration-de-jenkins-pour-le-projet)

## Installation de Minikube

Minikube est un outil qui permet d'exécuter Kubernetes localement. Il crée une machine virtuelle sur votre ordinateur qui exécute un cluster Kubernetes à nœud unique.

### Prérequis
- Un hyperviseur installé (comme VirtualBox, Hyper-V, Docker)
- 2 CPUs ou plus
- 2 Go de mémoire libre
- 20 Go d'espace disque libre
- Connexion Internet

### Installation sur Windows

1. **Téléchargement de Minikube**
   - Visitez la [page de téléchargement de Minikube](https://minikube.sigs.k8s.io/docs/start/)
   - Téléchargez l'installateur Windows (.exe)

2. **Installation via PowerShell (administrateur)**
   ```powershell
   New-Item -Path 'c:\' -Name 'minikube' -ItemType Directory -Force
   Invoke-WebRequest -OutFile 'c:\minikube\minikube.exe' -Uri 'https://github.com/kubernetes/minikube/releases/latest/download/minikube-windows-amd64.exe' -UseBasicParsing
   ```

3. **Ajout au PATH**
   ```powershell
   $oldPath = [Environment]::GetEnvironmentVariable('Path', [EnvironmentVariableTarget]::Machine)
   if ($oldPath.Split(';') -inotcontains 'C:\minikube') { 
     [Environment]::SetEnvironmentVariable('Path', $('{0};C:\minikube' -f $oldPath), [EnvironmentVariableTarget]::Machine) 
   }
   ```

4. **Démarrage de Minikube**
   ```powershell
   minikube start --driver=<driver_name>
   ```
   Remplacez `<driver_name>` par votre hyperviseur préféré (virtualbox, hyperv, docker).

5. **Vérification de l'installation**
   ```powershell
   minikube status
   ```

## Installation de kubectl

kubectl est l'outil en ligne de commande pour interagir avec les clusters Kubernetes.

### Installation sur Windows

1. **Téléchargement de kubectl**
   ```powershell
   Invoke-WebRequest -OutFile 'C:\minikube\kubectl.exe' -Uri 'https://dl.k8s.io/release/v1.28.0/bin/windows/amd64/kubectl.exe'
   ```

2. **Vérification de l'installation**
   ```powershell
   kubectl version --client
   ```

3. **Configuration de kubectl pour utiliser Minikube**
   ```powershell
   minikube kubectl -- get pods -A
   ```

4. **Création d'un alias (optionnel)**
   ```powershell
   New-Alias -Name kubectl -Value minikube kubectl --
   ```

## Installation de Jenkins

Jenkins est un serveur d'automatisation open-source qui permet de construire, tester et déployer des logiciels.

### Installation sur Windows

1. **Prérequis**
   - Java 11 ou supérieur installé
   - Téléchargez et installez [Java JDK](https://www.oracle.com/java/technologies/downloads/) si nécessaire

2. **Téléchargement de Jenkins**
   - Visitez [jenkins.io](https://www.jenkins.io/download/)
   - Téléchargez l'installateur Windows (.msi)
   - vous pouvez utiliser image jenkins/jenkins:lts si vous souhaitez utiliser Docker

3. **Installation**
   - L'installation par défaut place Jenkins sur http://localhost:8080/

4. **Configuration initiale**
   - Ouvrez un navigateur et accédez à http://localhost:8080/
   - Récupérez le mot de passe initial dans le fichier indiqué à l'écran
   ```powershell
   Get-Content "C:\Program Files\Jenkins\secrets\initialAdminPassword"
   ```
   - Installez les plugins suggérés
   - Créez un compte administrateur
   - Configurez l'URL de Jenkins

## Configuration de Jenkins pour le projet

Cette section explique comment configurer Jenkins pour le projet SOA Microservices.

### Configuration des plugins nécessaires

1. **Installation des plugins**
   - Accédez à "Administrer Jenkins" > "Gestion des plugins" > "Disponibles"
   - Recherchez et installez les plugins suivants:
     - Kubernetes
     - Docker Pipeline
     - Git Integration
     - Pipeline
     - SonarQube Scanner
     - Credentials Binding

2. **Redémarrage de Jenkins**
   - Cochez "Redémarrer Jenkins quand l'installation est terminée et qu'aucun job n'est en cours"

### Configuration des credentials

1. **Ajout des credentials Docker Hub**
   - Accédez à "Administrer Jenkins" > "Manage Credentials" > "Jenkins" > "Global credentials" > "Add Credentials"
   - Sélectionnez "Username with password"
   - ID: `dockerhub-creds`
   - Username: Votre nom d'utilisateur Docker Hub
   - Password: Votre mot de passe Docker Hub
   - Description: "Docker Hub Credentials"

2. **Ajout des credentials GitHub**
   - Accédez à "Administrer Jenkins" > "Manage Credentials" > "Jenkins" > "Global credentials" > "Add Credentials"
   - Sélectionnez "Username with password"
   - ID: `github-creds`
   - Username: Votre nom d'utilisateur GitHub
   - Password: Votre token d'accès personnel GitHub
   - Description: "GitHub Credentials"

### Configuration de SonarQube

1. **Configuration du serveur SonarQube**
   - Accédez à "Administrer Jenkins" > "Configurer le système"
   - Trouvez la section "SonarQube servers"
   - Cliquez sur "Add SonarQube"
   - Name: `sonarqube-local`
   - Server URL: `http://sonarqube-service.soa-microservices.svc.cluster.local:9000` (ou l'URL de votre serveur SonarQube)
   - Server authentication token: Ajoutez votre token SonarQube

### Configuration du cloud Kubernetes

1. **Configuration du cloud Kubernetes**
   - Accédez à "Administrer Jenkins" > "Configurer le système"
   - Trouvez la section "Cloud"
   - Cliquez sur "Add a new cloud" > "Kubernetes"
   - Name: `kubernetes`
   - Kubernetes URL: `https://kubernetes.default.svc.cluster.local` (si Jenkins est dans le cluster) ou utilisez `kubectl cluster-info` pour obtenir l'URL
   - Kubernetes Namespace: `soa-microservices`
   - Credentials: Sélectionnez ou créez les credentials pour accéder à Kubernetes

2. **Configuration des Pod Templates**
   - Dans la configuration du cloud Kubernetes, ajoutez un Pod Template:
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

### Création des pipelines

1. **Création d'un pipeline pour chatSOA**
   - Accédez à "Nouveau Item"
   - Entrez un nom (ex: "chatSOA-build")
   - Sélectionnez "Pipeline"
   - Cliquez sur "OK"
   - Dans la section "Pipeline":
     - Definition: "Pipeline script from SCM"
     - SCM: Git
     - Repository URL: `https://github.com/ines312692/Microservices_Social_Engagement_Architecture.git`
     - Credentials: Sélectionnez vos credentials GitHub
     - Branch Specifier: `*/feature/restructure-pipeline`
     - Script Path: `Jenkins_pipelines/build/chatSOA/Jenkinsfile`
   - Cliquez sur "Sauvegarder"

2. **Création des autres pipelines**
   - Répétez le processus pour les autres services (UserSOA, EngagementSOA, SOA_eureka)
   - Utilisez les chemins de Jenkinsfile appropriés pour chaque service

## Exécution du projet

Une fois que Minikube, kubectl et Jenkins sont installés et configurés, vous pouvez exécuter le projet:

1. **Démarrage de Minikube**
   ```powershell
   minikube start
   ```

2. **Création du namespace**
   ```powershell
   kubectl apply -f k8s/namespace.yaml
   ```

3. **Déploiement de l'infrastructure**
   ```powershell
   kubectl apply -f k8s/infrastructure/pvc/
   kubectl apply -f k8s/infrastructure/rbac/
   ```

4. **Exécution des pipelines Jenkins**
   - Accédez à Jenkins (http://localhost:8080/)
   - Lancez les pipelines de build pour chaque service

5. **Vérification des déploiements**
   ```powershell
   kubectl get pods -n soa-microservices
   ```

6. **Accès aux services**
   ```powershell
   minikube service list -n soa-microservices
   ```

## Dépannage

### Problèmes courants avec Minikube

1. **Minikube ne démarre pas**
   - Vérifiez que la virtualisation est activée dans le BIOS
   - Essayez un autre pilote: `minikube start --driver=virtualbox` ou `minikube start --driver=docker`

2. **Problèmes de mémoire**
   - Augmentez la mémoire allouée: `minikube start --memory=4096`

### Problèmes courants avec Jenkins

1. **Jenkins ne démarre pas**
   - Vérifiez les logs: `Get-Content "C:\Program Files\Jenkins\jenkins.err.log"`
   - Vérifiez que Java est correctement installé: `java -version`

2. **Problèmes de connexion à Kubernetes**
   - Vérifiez que le fichier kubeconfig est correctement configuré
   - Vérifiez les permissions du service account Jenkins dans Kubernetes

### Problèmes courants avec les pipelines

1. **Échec de build Docker**
   - Vérifiez que Docker est installé et accessible
   - Vérifiez les credentials Docker Hub

2. **Échec de déploiement Kubernetes**
   - Vérifiez les permissions RBAC
   - Vérifiez que les PVC sont créés: `kubectl get pvc -n soa-microservices`