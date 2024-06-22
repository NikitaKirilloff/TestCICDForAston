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
            TOMCAT_CREDS=credentials('pi-ssh-key')
            TOMCAT_SERVER="212.22.70.140"
            ROOT_WAR_LOCATION="/opt/tomcat/webapps/"
            LOCAL_WAR_DIR="/target"
            WAR_FILE="test.war"
          }

        steps {
                sh '''
                  ssh -i $TOMCAT_CREDS $TOMCAT_CREDS_USR@$TOMCAT_SERVER "/opt/tomcat/bin/catalina.sh stop"
                  ssh -i $TOMCAT_CREDS $TOMCAT_CREDS_USR@$TOMCAT_SERVER "rm -rf $ROOT_WAR_LOCATION/test; rm -f $ROOT_WAR_LOCATION/test.war"
                  scp -i $TOMCAT_CREDS $LOCAL_WAR_DIR/$WAR_FILE $TOMCAT_CREDS_USR@$TOMCAT_SERVER:$ROOT_WAR_LOCATION/test.war
                  ssh -i $TOMCAT_CREDS $TOMCAT_CREDS_USR@$TOMCAT_SERVER "chown $TOMCAT_CREDS_USR:$TOMCAT_CREDS_USR $ROOT_WAR_LOCATION/test.war"
                  ssh -i $TOMCAT_CREDS $TOMCAT_CREDS_USR@$TOMCAT_SERVER "/opt/tomcat/bin/catalina.sh start"
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