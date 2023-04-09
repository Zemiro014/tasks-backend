pipeline {
    agent any
    stages {
        stage ('Build Backend') {
            steps {
                sh 'mvn clean package -DskipTests=true'
            }
        }
        stage ('Unit Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage ('Static analysis with sonar') {
            environment {
                scannerHome = tool 'Sonar_Qube_Scanner'
            }
            steps {
                withSonarQubeEnv('Sonar_Qube_Server') {
                    sh "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://localhost:9000 -Dsonar.login=9451633542887c7764e19468711b9c825bbf62a8 -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/model/**,**Application.java"
                }
            }
        }
        // stage ('Quality Gate Sonar') {
        //     steps {
        //         sleep(20) {
        //             timeout(time: 1, unit: 'MINUTES') {
        //                 waitForQualityGate abortPipeline: true
        //             }
        //         }
        //     }
        // }

        stage ('Deploy Backend') {
            steps {
                deploy adapters: [tomcat8(credentialsId: 'TomCatId', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'
            }
        }

        stage ('API Test') {
            steps {
                dir('api-test') {
                    git credentialsId: 'GitHubId', url: 'https://github.com/Zemiro014/test-tasks-api'
                    sh 'mvn test'
                }
            }
        }

        stage ('Deploy Frontend') {
            steps {
                dir('frontend') {
                    git credentialsId: 'GitHubId', url: 'https://github.com/Zemiro014/tasks-frentend'
                    sh 'mvn clean package'
                    deploy adapters: [tomcat8(credentialsId: 'TomCatId', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks', war: 'target/tasks.war'                     
                }
            }
        }
    }
}