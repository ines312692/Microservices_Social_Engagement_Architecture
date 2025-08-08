#!/bin/bash
# Apply Kubernetes resources
kubectl apply -f Jenkins_As_Code/k8s/pvc/
kubectl apply -f Jenkins_As_Code/k8s/rbac/

# Install Jenkins Helm chart
helm install jenkins Jenkins_As_Code/helm --namespace soa-microservices \
  --set persistence.enabled=true \
  --set persistence.storageClass="" \
  --set persistence.size=8Gi

# Wait for Jenkins to be ready
kubectl wait --for=condition=ready pod -l app=jenkins --namespace soa-microservices --timeout=300s

echo "Jenkins deployed successfully. Access at http://<cluster-ip>:8080"