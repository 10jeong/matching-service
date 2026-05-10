pipeline {
    agent any

    tools {
        jdk 'JDK17'
    }

    environment {
        DOCKER_IMAGE = 'sunsik17/tripmate-matching'
        DOCKER_TAG = 'latest'
        MATCHING_EC2_IP = '172.31.34.59'
        PEM_PATH = '/home/ec2-user/tripmate.pem'
        GITHUB_USERNAME = credentials('github-token').username
        GITHUB_TOKEN = credentials('github-token').password
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew clean build -x test'
            }
        }

        stage('Docker Build & Push') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'docker-account',
                    usernameVariable: 'DOCKER_USERNAME',
                    passwordVariable: 'DOCKER_PASSWORD'
                )]) {
                    sh """
                        docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD}
                        docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} .
                        docker push ${DOCKER_IMAGE}:${DOCKER_TAG}
                    """
                }
            }
        }

        stage('Deploy') {
            steps {
                sh """
                    ssh -i ${PEM_PATH} -o StrictHostKeyChecking=no ec2-user@${MATCHING_EC2_IP} '
                        docker pull ${DOCKER_IMAGE}:${DOCKER_TAG}
                        docker stop matching-service || true
                        docker rm matching-service || true
                        docker run -d \\
                            --name matching-service \\
                            --env-file /home/ec2-user/.env \\
                            -p 8080:8080 \\
                            ${DOCKER_IMAGE}:${DOCKER_TAG}
                    '
                """
            }
        }
    }

    post {
        success {
            echo 'Deploy succeeded'
        }
        failure {
            echo 'Deploy failed'
        }
    }
}