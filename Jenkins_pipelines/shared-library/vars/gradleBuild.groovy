def call(String workDir, String gradleCachePath, boolean parallel = false) {
    dir(workDir) {
        sh 'chmod +x ./gradlew'
        def parallelFlag = parallel ? '--parallel' : ''
        sh "./gradlew build --no-daemon --build-cache ${parallelFlag} --project-cache-dir=${gradleCachePath}"
    }
}