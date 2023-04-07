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
                scannerHome = tool 'SONAR_SCANNER'
            }
            steps {
                withSonarQubeEnv('SONAR_LOCAL') {
                    sh "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://localhost:9000 -Dsonar.login=b77374db5126457a748304f7122d85f838b56157 -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/model/**,**Application.java"
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
        stages ('Quality Gate Sonar') {
            steps {
                // Run your SonarQube analysis and save the task id to a variable
                def analysisTask = withSonarQubeEnv('SONAR_SCANNER') {
                    sh 'mvn clean package sonar:sonar'
                }
            }
                    // Use the task id to wait for the Quality Gate to complete
            timeout(time: 1, unit: 'HOURS') {
                def qg = waitForQualityGate abortPipeline: true
                    if (qg.status != 'OK') {
                        error "Pipeline aborted due to Quality Gate failure: ${qg.status}"
                    }
            }
        }
    }
}