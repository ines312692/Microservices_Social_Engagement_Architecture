output "eureka_service_endpoint" {
  description = "Endpoint for Eureka Server"
  value       = helm_release.eureka_server.metadata[0].name
}