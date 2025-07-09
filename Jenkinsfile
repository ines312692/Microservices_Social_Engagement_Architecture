pipeline {
    agent any
    environment {
        GRADLE_USER_HOME = "${WORKSPACE}\\.gradle" // Enable Gradle caching
    }
    stages {
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], userRemoteConfigs: [[url: 'https://github.com/ines312692/SOA.git']], shallow: true])
            }
        }
        stage('Build Microservices') {
            parallel {
                stage('Build engagementSOA') {
                    steps {
                        bat 'gradlew :engagementSOA:build --build-cache'
                    }
                }
                stage('Build chatSOA') {
                    steps {
                        bat 'gradlew :chatSOA:build --build-cache'
                    }
                }
                stage('Build userSOA') {
                    steps {
                        bat 'gradlew :userSOA:build --build-cache'
                    }
                }
            }
        }
        stage('Run Tests') {
            steps {
                bat 'gradlew test'
            }
        }
    }
    post {
        success {
            echo 'Build and tests completed successfully!'
        }
        failure {
            echo 'Build or tests failed!'
        }
    }
}