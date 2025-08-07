FROM jenkins/jenkins:2.426.3-lts-jdk17

# Installer en tant que root pour les packages système
USER root

# Installer Docker CLI et kubectl
RUN apt-get update && apt-get install -y \\
    docker.io \\
    kubectl \\
    curl \\
    && rm -rf /var/lib/apt/lists/*

# Installer Helm
RUN curl https://get.helm.sh/helm-v3.13.0-linux-amd64.tar.gz | tar -xzO linux-amd64/helm > /usr/local/bin/helm \\
    && chmod +x /usr/local/bin/helm

# Revenir à l'utilisateur Jenkins
USER jenkins

# Copier la configuration JCasC
COPY Jenkins/jcasc/jenkins.yaml /var/jenkins_home/casc_configs/jenkins.yaml
COPY Jenkins/jcasc/plugins.txt /usr/share/jenkins/ref/plugins.txt

# Installer les plugins
RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.txt

# Variables d'environnement pour JCasC
ENV CASC_JENKINS_CONFIG=/var/jenkins_home/casc_configs/jenkins.yaml
ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false ${JAVA_OPTS:-}"