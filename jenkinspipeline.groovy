#!groovy
/*
  This ci.pipeline is the master of all other ci.pipeline
  with this ci.pipeline you can change the environment and the scope of the test
 */

pipeline {
    agent any

    options {
        buildDiscarder logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '10')
        disableConcurrentBuilds()
    }



    tools {
        maven 'maven'
        jdk 'jdk11'
    }


    stages {
        stage('Compile') {
            steps {
                withMaven(
                        jdk: 'jdk11',
                        maven: 'maven',

                ) {
                    //On all environment we need to compile all tests
                    echo "------------- Start tests compilation packaging --------------"
                    script {
                        sh 'mvn clean compile'
                        sh 'mvn -DskipTests=true package'
                    }
                    echo "-------------- End tests compilation --------------"
                }//end with maven
            }//end step
        }//end stage compilation

        // --------------------------------------------------------------------------------------------------------------------------
        stage('Run Test Suite On Dineazy') {
            steps {
                withMaven(
                        jdk: 'jdk11',
                        maven: 'maven',

                ) {
                    echo "------------- Start Tests --------------"
                    script {
                        try {
                            sh 'mvn verify'
                        } catch (err) {
                            echo 'Error to run on tests'
                        }
                    }
                    echo "-------------- End Tests --------------"
                }//end with maven
            }//end step
        }//end stage
        // --------------------------------------------------------------------------------------------------------------------------

    }//end stages

    post {
        success {
            script {
                currentBuild.result = "SUCCESS"
            }
            emailext(
                    attachLog: true,
                    attachmentsPattern: '*.log',
                    to: 'sabapathi.a@itprofound.com',
                    mimeType: 'text/html',
                    subject: currentBuild.fullDisplayName + " - " + currentBuild.result,
                    body: '${FILE, path="//target/cucumber-reports/cucumber-html-report.html"}',
                    recipientProviders: [[$class: 'DevelopersRecipientProvider']]
            )
            cleanWs()
        }
        unstable {
            emailext(
                    attachLog: true,
                    attachmentsPattern: '*.log',
                    to: 'sabapathi.a@itprofound.com',
                    mimeType: 'text/html',
                    subject: currentBuild.fullDisplayName + " - " + currentBuild.result,
                    body: '${FILE, path="//target/cucumber-reports/cucumber-html-report.html"}',
                    recipientProviders: [[$class: 'DevelopersRecipientProvider']]
            )
            cleanWs()
        }
        failure {
            emailext(
                    attachLog: true,
                    attachmentsPattern: '*.log',
                    to: 'sabapathi.a@itprofound.com',
                    mimeType: 'text/html',
                    subject: currentBuild.fullDisplayName + " - " + currentBuild.result,
                    body: '${FILE, path="//target/cucumber-reports/cucumber-html-report.html"}',
                    recipientProviders: [[$class: 'DevelopersRecipientProvider']]
            )
            cleanWs()
        }
        always {
            script {
                if (env.TEST_SCOPE != "NOTEST") {
                    publishHTML([allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true, reportDir: 'target/cucumber-reports', reportFiles: 'cucumber-html-report.html', reportTitles: 'SinglePageReport', reportName: 'SinglePageReport'])
                } else {
                    echo 'Build without tests'
                }
            }
        }
    }

}