#!groovy

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
        stage("Tests") {
            steps {
                sh('''#!/bin/bash -ex
echo "** Building tests docker image started" && \\
docker build --target build -t architectureplayground/feature-toggle:tests . && \\
echo "** Building tests docker image finished" && \\

echo "** Tests started" && \\
docker run -i --rm -v /var/run/docker.sock:/var/run/docker.sock architectureplayground/feature-toggle:tests && \\
echo "** Tests finished"
''')
            }
        }
    }
}