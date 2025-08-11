def call(String workDir, String artifactsPvcPath, String serviceName, String buildNumber) {
    sh """
    mkdir -p ${artifactsPvcPath}/${serviceName}-${buildNumber}
    cp -r ${workDir}/build/libs/*.jar ${artifactsPvcPath}/${serviceName}-${buildNumber}/ || true
  """
}