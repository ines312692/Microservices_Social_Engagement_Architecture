FROM jenkins/jenkins:lts
USER root
RUN apt-get update && apt-get install -y docker.io kubectl
RUN curl -fsSL https://get.helm.sh/helm-v3.15.4-linux-amd64.tar.gz | tar -xz && mv linux-amd64/helm /usr/local/bin/helm
USER jenkins
COPY config/plugins.yaml /usr/share/jenkins/ref/plugins.yaml
RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.yaml
ENV CASC_JENKINS_CONFIG=/var/jenkins_home/casc_configs
COPY config/jenkins.yaml /var/jenkins_home/casc_configs/jenkins.yaml
COPY config/credentials.yaml /var/jenkins_home/casc_configs/credentials.yaml