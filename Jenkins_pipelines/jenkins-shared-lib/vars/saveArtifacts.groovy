def call(String workDir, String artifactsPath, String buildNumber, String serviceName) {
    try {
        container('docker') {
            sh """
                mkdir -p ${artifactsPath}/${serviceName}-${buildNumber}
                cp -r ${workDir}/* ${artifactsPath}/${serviceName}-${buildNumber}/ || true
            """
        }
    } catch (Exception e) {
        echo "Erreur dans le stage 'Save Artifacts' : ${e.message}"
        error("Échec de la sauvegarde des artefacts")
    }
}