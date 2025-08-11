def call(String credentialsId) {
    try {
        withCredentials([usernamePassword(credentialsId: credentialsId, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
            sh 'echo "$PASSWORD" | docker login -u "$USERNAME" --password-stdin'
        }
    } catch (Exception e) {
        echo "Erreur dans le stage 'Docker Login' : ${e.message}"
        error("Échec login Docker")
    }
}