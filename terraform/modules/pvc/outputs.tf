output "pvc_names" {
  description = "List of Persistent Volume Claims created"
  value       = [
    kubernetes_persistent_volume_claim.eureka_pvc.metadata[0].name,
    kubernetes_persistent_volume_claim.gradle_cache_usersoa.metadata[0].name,
    kubernetes_persistent_volume_claim.artifacts_usersoa.metadata[0].name,
    kubernetes_persistent_volume_claim.gradle_cache_chatsoa.metadata[0].name,
    kubernetes_persistent_volume_claim.artifacts_chatsoa.metadata[0].name,
    kubernetes_persistent_volume_claim.gradle_cache_engagementsoa.metadata[0].name,
    kubernetes_persistent_volume_claim.artifacts_engagementsoa.metadata[0].name,
  ]
}