def call(String credentialsId) {
    withCredentials([usernamePassword(
            credentialsId: credentialsId,
            usernameVariable: 'USERNAME',
            passwordVariable: 'PASSWORD'
    )]) {
        sh 'echo "$PASSWORD" | docker login -u "$USERNAME" --password-stdin'
    }
}