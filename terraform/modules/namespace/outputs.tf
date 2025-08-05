output "namespace_name" {
  description = "Name of the created namespace"
  value       = kubernetes_namespace.soa.metadata[0].name
}