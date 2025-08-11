def call(String dockerhubUsername, String dockerhubImage, String imageTag) {
    sh """
    docker push ${dockerhubUsername}/${dockerhubImage}:${imageTag}
    docker push ${dockerhubUsername}/${dockerhubImage}:latest
  """
}