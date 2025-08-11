def call(String dockerhubUsername, String dockerhubImage, String imageTag, String workDir, boolean useBuildx = false) {
    dir(workDir) {
        def buildCommand = useBuildx ?
                """
      docker buildx create --use --name mybuilder || true
      docker buildx build \
        --cache-from=type=registry,ref=${dockerhubUsername}/${dockerhubImage}:cache \
        --cache-to=type=registry,ref=${dockerhubUsername}/${dockerhubImage}:cache,mode=max \
        --build-arg BUILDKIT_INLINE_CACHE=1 \
        --push \
        -t ${dockerhubUsername}/${dockerhubImage}:${imageTag} \
        -t ${dockerhubUsername}/${dockerhubImage}:latest .
      """ :
                """
      docker build \
        --build-arg BUILDKIT_INLINE_CACHE=1 \
        -t ${dockerhubUsername}/${dockerhubImage}:${imageTag} \
        -t ${dockerhubUsername}/${dockerhubImage}:latest .
      """
        sh buildCommand
    }
}