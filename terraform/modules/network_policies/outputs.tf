output "network_policies" {
  description = "List of network policies created"
  value       = [
    kubernetes_network_policy.default_deny_all.metadata[0].name,
    kubernetes_network_policy.allow_egress.metadata[0].name,
    kubernetes_network_policy.allow_to_eureka.metadata[0].name,
    kubernetes_network_policy.allow_user_to_chat.metadata[0].name,
    kubernetes_network_policy.allow_engagement_to_user.metadata[0].name,
  ]
}