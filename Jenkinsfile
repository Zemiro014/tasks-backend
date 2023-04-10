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
        //         // sleep(20) {
        //         //     timeout(time: 1, unit: 'HOURS') {
        //         //         waitForQualityGate abortPipeline: true
        //         //     }
        //         // }
        //         waitForQualityGate abortPipeline: false, credentialsId: 'JenkinsToken'
        //     }
        // }
        // tage ('Quality Gate Sonar') {
        //     timeout(time: 1, unit: 'HOURS') {
        //         def qg = waitForQualityGate()
        //         if (qg.status != 'OK') {
        //             error "Pipeline aborted due to quality gate failure: ${qg.status}"
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

        stage ('Functional Test') {
            steps {
                dir('functional-test') {
                    git credentialsId: 'GitHubId', url: 'https://github.com/Zemiro014/tasks-functional-test'
                    sh 'mvn test'               
                }
            }
        }
    }
}