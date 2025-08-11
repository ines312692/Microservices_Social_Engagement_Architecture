def call(String workDir, String gradleCachePath) {
    try {
        container('gradle') {
            dir(workDir) {
                sh '''
                    chmod +x ./gradlew
                    export GRADLE_USER_HOME=${gradleCachePath}
                    ./gradlew build --parallel --max-workers=4 --project-cache-dir=${gradleCachePath}
                    ls -l build/libs
                    ls -l /mnt/gradle_cache/wrapper/dists || echo "Dossier vide"
                '''
            }
        }
    } catch (Exception e) {
        echo "Erreur dans le stage 'Build' : ${e.message}"
        error("Échec de la compilation")
    }
}