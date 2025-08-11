def call(boolean useBuildx = false) {
    if (useBuildx) {
        sh 'docker buildx rm mybuilder || true'
    }
    sh 'docker system prune -f || true'
    sh 'docker logout || true'
}