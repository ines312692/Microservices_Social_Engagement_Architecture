resource "helm_release" "eureka_server" {
  name       = "eureka-server"
  chart      = "${var.helm_chart_path}/eureka-server"
  namespace  = var.namespace
  set {
    name  = "image.repository"
    value = "${var.container_registry}/eureka-server"
  }
  set {
    name  = "image.tag"
    value = var.image_tag
  }
  set {
    name  = "service.port"
    value = "8761"
  }
}

resource "helm_release" "user_soa" {
  name       = "user-soa"
  chart      = "${var.helm_chart_path}/user-soa"
  namespace  = var.namespace
  set {
    name  = "image.repository"
    value = "${var.container_registry}/user-soa"
  }
  set {
    name  = "image.tag"
    value = var.image_tag
  }
  set {
    name  = "service.port"
    value = "8081"
  }
  depends_on = [helm_release.eureka_server]
}

resource "helm_release" "chat_soa" {
  name       = "chat-soa"
  chart      = "${var.helm_chart_path}/chat-soa"
  namespace  = var.namespace
  set {
    name  = "image.repository"
    value = "${var.container_registry}/chat-soa"
  }
  set {
    name  = "image.tag"
    value = var.image_tag
  }
  set {
    name  = "service.port"
    value = "8082"
  }
  depends_on = [helm_release.eureka_server]
}

resource "helm_release" "engagement_soa" {
  name       = "engagement-soa"
  chart      = "${var.helm_chart_path}/engagement-soa"
  namespace  = var.namespace
  set {
    name  = "image.repository"
    value = "${var.container_registry}/engagement-soa"
  }
  set {
    name  = "image.tag"
    value = var.image_tag
  }
  set {
    name  = "service.port"
    value = "8083"
  }
  depends_on = [helm_release.eureka_server]
}