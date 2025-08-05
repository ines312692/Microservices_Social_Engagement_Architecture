resource "kubernetes_namespace" "soa" {
  metadata {
    name = var.namespace
  }
}