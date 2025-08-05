resource "kubernetes_persistent_volume_claim" "eureka_pvc" {
  metadata {
    name      = "eureka-pvc"
    namespace = var.namespace
  }
  spec {
    access_modes = ["ReadWriteOnce"]
    resources {
      requests = {
        storage = "500Mi"
      }
    }
  }
}

resource "kubernetes_persistent_volume_claim" "gradle_cache_usersoa" {
  metadata {
    name      = "gradle-cache-usersoa"
    namespace = var.namespace
  }
  spec {
    access_modes = ["ReadWriteOnce"]
    resources {
      requests = {
        storage = "1Gi"
      }
    }
  }
}

resource "kubernetes_persistent_volume_claim" "artifacts_usersoa" {
  metadata {
    name      = "artifacts-usersoa"
    namespace = var.namespace
  }
  spec {
    access_modes = ["ReadWriteOnce"]
    resources {
      requests = {
        storage = "1Gi"
      }
    }
  }
}

resource "kubernetes_persistent_volume_claim" "gradle_cache_chatsoa" {
  metadata {
    name      = "gradle-cache-chatsoa"
    namespace = var.namespace
  }
  spec {
    access_modes = ["ReadWriteOnce"]
    resources {
      requests = {
        storage = "1Gi"
      }
    }
  }
}

resource "kubernetes_persistent_volume_claim" "artifacts_chatsoa" {
  metadata {
    name      = "artifacts-chatsoa"
    namespace = var.namespace
  }
  spec {
    access_modes = ["ReadWriteOnce"]
    resources {
      requests = {
        storage = "1Gi"
      }
    }
  }
}

resource "kubernetes_persistent_volume_claim" "gradle_cache_engagementsoa" {
  metadata {
    name      = "gradle-cache-engagementsoa"
    namespace = var.namespace
  }
  spec {
    access_modes = ["ReadWriteOnce"]
    resources {
      requests = {
        storage = "1Gi"
      }
    }
  }
}

resource "kubernetes_persistent_volume_claim" "artifacts_engagementsoa" {
  metadata {
    name      = "artifacts-engagementsoa"
    namespace = var.namespace
  }
  spec {
    access_modes = ["ReadWriteOnce"]
    resources {
      requests = {
        storage = "1Gi"
      }
    }
  }
}