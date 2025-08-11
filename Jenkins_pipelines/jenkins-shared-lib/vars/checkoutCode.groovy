def call(Map config) {
    try {
        checkout([
                $class: 'GitSCM',
                branches: [[name: config.branch ?: '*/main']],
                userRemoteConfigs: [[url: config.gitUrl]],
                extensions: [
                        [$class: 'CloneOption', depth: 1, noTags: false, shallow: true]
                ]
        ])
    } catch (Exception e) {
        echo "Erreur dans le stage 'Checkout Last Commit Only' : ${e.message}"
        error("Échec du checkout Git")
    }
}