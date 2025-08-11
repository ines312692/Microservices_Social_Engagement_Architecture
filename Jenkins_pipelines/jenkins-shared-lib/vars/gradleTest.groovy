def call(String workDir, String gradleCachePath) {
    when {
        changeset "**/*.java"
    }
    try {
        container('gradle') {
            dir(workDir) {
                sh 'chmod +x ./gradlew'
                sh "./gradlew test --parallel --max-workers=4 --project-cache-dir=${gradleCachePath}"
            }
        }
    } catch (Exception e) {
        echo "Erreur dans le stage 'Unit Tests' : ${e.message}"
        error("Échec des tests unitaires")
    }
}