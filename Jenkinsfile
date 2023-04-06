pipeline {
    agent any
    stages {
        stage ('Início') {
            steps {
                sh 'echo início'
            }
        }
        stage ('Meio') {
            steps {
                sh 'echo meio'
                
                echo 'echo meio de novo'
            }
        }
        stage ('Fim') {
            steps {
                sleep(5)
                sh 'echo fim'
            }
        }
    }
}