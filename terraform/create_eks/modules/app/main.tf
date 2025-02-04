resource "kubernetes_namespace" "citizen" {
  metadata {
    name = "citizen"
  }
}

resource "kubernetes_manifest" "app_manifests" {
  for_each = fileset("../../kubernetes/eks", "*.yaml")

  manifest = yamldecode(file("../../kubernetes/eks/${each.key}"))
}