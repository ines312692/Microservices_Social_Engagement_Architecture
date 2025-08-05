variable "namespace" {
  description = "Kubernetes namespace for Helm releases"
  type        = string
}

variable "container_registry" {
  description = "Container registry for Docker images"
  type        = string
}

variable "image_tag" {
  description = "Docker image tag for services"
  type        = string
}

variable "helm_chart_path" {
  description = "Path to Helm charts"
  type        = string
}