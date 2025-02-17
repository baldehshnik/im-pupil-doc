pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                script {
                    sh 'chmod +x gradlew'
                    sh './gradlew build'
                }
            }
        }
    }
}



























