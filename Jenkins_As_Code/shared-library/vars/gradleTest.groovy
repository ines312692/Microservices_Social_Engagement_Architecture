def call(String workDir, String gradleCachePath) {
    dir(workDir) {
        sh 'chmod +x ./gradlew'
        sh "./gradlew test --project-cache-dir=${gradleCachePath}"
    }
}