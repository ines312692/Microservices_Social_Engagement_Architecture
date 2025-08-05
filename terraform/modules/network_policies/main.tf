resource "kubernetes_network_policy" "default_deny_all" {
  metadata {
    name      = "default-deny-all"
    namespace = var.namespace
  }
  spec {
    pod_selector {}
    policy_types = ["Ingress"]
  }
}

resource "kubernetes_network_policy" "allow_egress" {
  metadata {
    name      = "allow-egress"
    namespace = var.namespace
  }
  spec {
    pod_selector {}
    policy_types = ["Egress"]
    egress {}
  }
}

resource "kubernetes_network_policy" "allow_to_eureka" {
  metadata {
    name      = "allow-to-eureka"
    namespace = var.namespace
  }
  spec {
    pod_selector {
      match_labels = {
        app = "eureka"
      }
    }
    ingress {
      from {
        pod_selector {}
      }
    }
    policy_types = ["Ingress"]
  }
}

resource "kubernetes_network_policy" "allow_user_to_chat" {
  metadata {
    name      = "allow-user-to-chat"
    namespace = var.namespace
  }
  spec {
    pod_selector {
      match_labels = {
        app = "chatsoa"
      }
    }
    ingress {
      from {
        pod_selector {
          match_labels = {
            app = "usersoa"
          }
        }
      }
    }
    policy_types = ["Ingress"]
  }
}

resource "kubernetes_network_policy" "allow_engagement_to_user" {
  metadata {
    name      = "allow-engagement-to-user"
    namespace = var.namespace
  }
  spec {
    pod_selector {
      match_labels = {
        app = "usersoa"
      }
    }
    ingress {
      from {
        pod_selector {
          match_labels = {
            app = "engagementsoa"
          }
        }
      }
    }
    policy_types = ["Ingress"]
  }
}