pipeline {
    agent any
    stages {
        stage('---clean---') {
            steps {
                sh "mvn clean"
            }
        }
        stage('--test--') {
            steps {
                sh "mvn test"
            }
        }
        stage('--package--') {
            steps {
            	sh "mvn package"
            }
        }
        stage('--deploy--') {
            steps {
            	sh "mvn deploy"
            }
        }
        stage('--dockerhub--') {
        	steps {
        		sh "docker build -t book-project ."
        		sh "docker push lukecottenham/book-project:latest"
        	}
        }
        
    }
}
