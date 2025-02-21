pipeline {
    agent any

    environment {
        DATABASE_URL = credentials('database-url')
        DATABASE_USER = credentials('database-user')
        DATABASE_PASSWORD = credentials('database-password')
        SERVER_URL = credentials('server-url')
        SERVER_PORT = credentials('server-port')
        REFRESH_TOKEN_EXPIRATION_DATE = credentials('refresh-token-expiration-date')
        JSON_TOKEN_SIGNING_KEY = credentials('json-token-signing-key')
        S3_ENDPOINT_URL = credentials('s3-endpoint-url')
        S3_ACCESS_KEY = credentials('s3-access-key')
        S3_BUCKET_NAME = credentials('s3-bucket-name')
        JENKINS_ADMIN_EMAIL = credentials('jenkins-admin-email')
    }

    stages {
        stage('Build') {
            steps {
                sh 'chmod +x ./mvnw'
                sh './mvnw clean compile'
            }
        }
    }

    post {
        success {
            emailext(
                    to: "${env.JENKINS_ADMIN_EMAIL}",
                    subject: "Build Succeeded: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                    body: "The build was successful! Please check the results.\n\nBuild link: ${env.BUILD_URL}"
            )
        }
        failure {
            emailext(
                    to: "${env.JENKINS_ADMIN_EMAIL}",
                    subject: "Build Failure: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                    body: "The build has failed. Please take a look.\n\nBuild link: ${env.BUILD_URL}"
            )
        }
    }
}



























