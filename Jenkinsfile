pipeline {
    agent any

    environment {
        TOMCAT_SERVER="212.22.70.140"
        ROOT_WAR_LOCATION="/opt/tomcat/webapps/"
        LOCAL_WAR_DIR="/var/lib/jenkins/workspace/Test/target"
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
               script {
                   // Остановка Tomcat
                   sh "ssh -i /var/lib/jenkins/.ssh/id_rsa ubuntu@212.22.70.140 sudo -u tomcat /opt/tomcat/bin/catalina.sh stop"

                   // Удаление старых файлов приложения
                   sh "ssh -i /var/lib/jenkins/.ssh/id_rsa ubuntu@212.22.70.140 'sudo rm -rf /opt/tomcat/webapps/test; sudo rm -f /opt/tomcat/webapps/test.war'"

                   // Копирование нового war файла
                   sh "scp -i /var/lib/jenkins/.ssh/id_rsa /var/lib/jenkins/workspace/Test/target/test.war ubuntu@212.22.70.140:/opt/tomcat/webapps/test.war"

                   // Установка владельца для war файла
                   sh "ssh -i /var/lib/jenkins/.ssh/id_rsa ubuntu@212.22.70.140 sudo chown tomcat:tomcat /opt/tomcat/webapps/test.war"

                   // Запуск Tomcat с установленным JAVA_HOME
                   sh "ssh -i /var/lib/jenkins/.ssh/id_rsa ubuntu@212.22.70.140 'sudo -u tomcat JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64 /opt/tomcat/bin/catalina.sh start'"
               }
           }
        }
    }

    post {
        always {
            sh "rm -rf ${LOCAL_WAR_DIR}/${WAR_FILE}"
        }
    }
}
