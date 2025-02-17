pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                script {
                    env.LOAD_DOTENV = true
                    load '${WORKSPACE}/.env'

                    sh './mvnw clean spring-boot:run'
                }
            }
        }
    }
}



























