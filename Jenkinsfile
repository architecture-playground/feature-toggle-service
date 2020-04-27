#!groovy
@Library('jenkins-libraries')_


properties([disableConcurrentBuilds()])

pipeline {
    agent {
        label 'master'
    }
    triggers { pollSCM('* * * * *') }
    options {
        buildDiscarder(logRotator(numToKeepStr: '10', artifactNumToKeepStr: '10'))
        timestamps()
    }
    stages {
        stage("Say hello world") {
            steps {
                helloWorld("say hello")
            }
        }
        stage("Tests") {
            steps {
                sh('''#!/bin/bash -ex
echo "** Building tests docker image started" && \\
docker build --target build -t architectureplayground/featuretoggle:tests . && \\
echo "** Building tests docker image finished" && \\

echo "** Tests started" && \\
docker run -i --rm -v /var/run/docker.sock:/var/run/docker.sock architectureplayground/featuretoggle:tests && \\
echo "** Tests finished"
''')
            }
        }
        stage("Push Docker Image") {
            steps {
                echo "** Docker login started"
                withCredentials([usernamePassword(credentialsId: 'dockerhub_architectureplayground', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                    sh '''docker login -u $USERNAME -p $PASSWORD'''
                }
                echo "** Docker login finished"

                sh '''#!/bin/bash -ex
echo "** Building application docker image started" && \\
docker build --target app -t architectureplayground/featuretoggle:latest . && \\
echo "** Building application docker image finished" && \\

echo "** Start pushing docker image in docker hub repository" && \\
docker push architectureplayground/featuretoggle:latest && \\
echo "** Docker image pushed to docker hub repository"
                    '''
            }
        }
    }
}