pipeline {
agent any

stages {
    stage('Checkout') {
        steps {
            checkout scm
        }
    }

    stage('Build') {
        steps {
             sh 'mvn clean package'
        }
    }

    stage('Deploy') {
        environment {
            REMOTE_HOST = 'http://212.22.70.140:8085/'
            REMOTE_USER = 'tomcat'
            REMOTE_PATH = '/path/to/tomcat/webapps/'
            WAR_FILE = 'target/TestCICDAston-1.0-SNAPSHOT.war'
        }

        steps {
             sh "ssh ${REMOTE_USER}@${REMOTE_HOST} \"${TOMCAT_HOME}/bin/shutdown.sh\""

             sh "ssh ${REMOTE_USER}@${REMOTE_HOST} \"rm -rf ${REMOTE_PATH}TestCICDAston-1.0-SNAPSHOT*\""

             sh "scp ${LOCAL_FILE} ${REMOTE_USER}@${REMOTE_HOST}:${REMOTE_PATH}"

              sh "ssh ${REMOTE_USER}@${REMOTE_HOST} \"${TOMCAT_HOME}/bin/startup.sh\""
        }
    }

    stage('Verify') {
        steps {
            sh "sleep 30"

            sh "curl -s http://localhost:8085/TestCICDAston-1.0-SNAPSHOT/"
        }
    }
}

post {
    always {
        // Cleanup any leftover files after deployment
        sh "rm -rf ${WAR_FILE}"
    }
}
}