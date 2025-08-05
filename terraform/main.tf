module "namespace" {
  source    = "./modules/namespace"
  namespace = var.namespace
}

module "network_policies" {
  source    = "./modules/network_policies"
  namespace = module.namespace.namespace_name
}

module "pvc" {
  source    = "./modules/pvc"
  namespace = module.namespace.namespace_name
}

module "helm" {
  source             = "./modules/helm"
  namespace          = module.namespace.namespace_name
  container_registry = var.container_registry
  image_tag         = var.image_tag
  helm_chart_path   = var.helm_chart_path
}