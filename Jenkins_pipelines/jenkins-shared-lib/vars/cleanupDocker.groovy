def call() {
    try {
        container('docker') {
            sh 'docker system prune -f || true'
        }
    } catch (Exception e) {
        echo "Erreur dans le stage 'Nettoyage' : ${e.message}"
        error("Échec lors du nettoyage")
    }
}