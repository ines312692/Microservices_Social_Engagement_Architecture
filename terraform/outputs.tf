output "namespace" {
  description = "Kubernetes namespace where resources are deployed"
  value       = module.namespace.namespace_name
}

output "eureka_server_url" {
  description = "URL for Eureka Server dashboard"
  value       = "http://${module.helm.eureka_service_endpoint}:8761"
}