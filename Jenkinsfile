pipeline {
agent any

environment {
    TOMCAT_SERVER="212.22.70.140"
    ROOT_WAR_LOCATION="/opt/tomcat/webapps/"
    LOCAL_WAR_DIR="/target"
    WAR_FILE="test.war"
}
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
        steps {
                sh '''
                  ssh -i /var/lib/jenkins/.ssh/id_rsa ubuntu@212.22.70.140 'bash /opt/tomcat/bin/catalina.sh stop'"
                  ssh -i ubuntu@$TOMCAT_SERVER "rm -rf $ROOT_WAR_LOCATION/test; rm -f $ROOT_WAR_LOCATION/test.war"
                  scp -i $LOCAL_WAR_DIR/$WAR_FILE ubuntu@$TOMCAT_SERVER:$ROOT_WAR_LOCATION/test.war
                  ssh -i ubuntu@$TOMCAT_SERVER "chown ubuntu:ubuntu $ROOT_WAR_LOCATION/test.war"
                  ssh -i ubuntu@$TOMCAT_SERVER "/opt/tomcat/bin/catalina.sh start"
                '''
              }
    }
}

post {
    always {
        sh "rm -rf ${WAR_FILE}"
    }
}
}