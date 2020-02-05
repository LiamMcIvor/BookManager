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
        stage('--docker-build--') {
        	steps {
        		sh "docker build -t lukecottenham/book-project:$BUILD_NUMBER -t lukecottenham/book-project:latest ."
        	}
        }
        stage('--dockerhub-push--') {
        	steps {
        		withDockerRegistry([ credentialsId: "luke-docker", url: "" ]) {
        			sh "docker push lukecottenham/book-project:$BUILD_NUMBER"
        			sh "docker push lukecottenham/book-project:latest"
        		}
        	}
        }
        stage('--nexus-deploy--') {
            steps {
            	sh "mvn deploy"
            }
        }
        stage('--test-deploy--') {
            steps {
            	sh "ssh -i /home/ubuntu/Project.pem ubuntu@ec2-35-176-134-117.eu-west-2.compute.amazonaws.com"
            	sh "./docker-back-end.sh"
            	sh "exit"
            }
        }
        
        
        
    }
}
