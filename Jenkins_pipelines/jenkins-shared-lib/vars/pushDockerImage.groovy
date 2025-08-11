def call(Map config) {
    try {
        container('docker') {
            dir(config.workDir) {
                sh """
                    docker buildx create --use --name mybuilder || true
                    docker buildx build \
                        --cache-from=type=registry,ref=${config.dockerHubUsername}/${config.dockerHubImage}:cache \
                        --cache-to=type=registry,ref=${config.dockerHubUsername}/${config.dockerHubImage}:cache,mode=max \
                        --build-arg BUILDKIT_INLINE_CACHE=1 \
                        --push \
                        -t ${config.dockerHubUsername}/${config.dockerHubImage}:${config.imageTag} \
                        -t ${config.dockerHubUsername}/${config.dockerHubImage}:latest .
                """
            }
        }
    } catch (Exception e) {
        echo "Erreur dans le stage 'Build and Push Image with Cache' : ${e.message}"
        error("Échec de la construction et push de l'image Docker")
    }
}