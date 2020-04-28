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
        stage("print branch name") {
            steps {
                sh ('printenv')
                print(env.GIT_BRANCH)
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
        stage('check branch and push to Docker hub repository') {
            checkBranchAndPushImage()

        }
    }
}