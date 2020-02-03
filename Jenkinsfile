pipeline {
    agent any
    stages {
        stage('---clean---') {
            steps {
            	sh "chmod +x chromedriver"
                sh "mvn clean"
            }
        }
        stage('--test--') {
            steps {
                sh "chmod +x chromedriver"
                sh "mvn test"
            }
        }
        stage('--package--') {
            steps {
            sh "chmod +x chromedriver"
            sh "mvn package"
            }
        }
    }
}
