variable "namespace" {
  description = "Kubernetes namespace for the SOA microservices"
  default     = "soa-microservices"
}

variable "container_registry" {
  description = "Container registry URL (e.g., docker.io, <aws_account_id>.dkr.ecr.<region>.amazonaws.com)"
  default     = "docker.io"
}

variable "image_tag" {
  description = "Docker image tag for all services"
  default     = "latest"
}

variable "helm_chart_path" {
  description = "Path to local Helm charts"
  default     = "../helm_charts"
}