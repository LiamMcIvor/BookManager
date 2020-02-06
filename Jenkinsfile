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
            	sh "ssh -T -i /home/jenkins/Project.pem ubuntu@ec2-3-10-162-181.eu-west-2.compute.amazonaws.com ./docker-back-end.sh"
            }
        }
        
        
        
    }
}
