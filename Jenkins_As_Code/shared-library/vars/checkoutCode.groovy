def call(String branch, String repoUrl, boolean shallow = true, int depth = 1) {
    checkout([
            $class: 'GitSCM',
            branches: [[name: "*/${branch}"]],
            userRemoteConfigs: [[url: repoUrl]],
            extensions: [
                    [$class: 'CloneOption', depth: depth, noTags: false, shallow: shallow]
            ]
    ])
}