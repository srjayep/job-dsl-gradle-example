String basePath = 'Docker API-Gateway Base Image Build'
String repo = 'adobe-apis/api-gateway'

folder(basePath) {
    description 'Docker API-Gateway Base Image Build'
}

job("$basePath/Docker Image Example") {
    scm {
        github repo
    }

    triggers {
        scm 'H/5 * * * *'
    }

    logRotator {
        numToKeep 5
    }

     parameters {
        stringParam ('FORCE_TAG', 'gateway-')
        stringParam ('DOCKER_REPO', 'docker-api-platform-snapshot/apiplatform')
	stringParam ('GIT_REPO', 'adobe-apis/apigateway')
        choiceParam('GIT_TYPE', ['PUBLIC', 'PRIVATE'])
	stringParam 'RELEASE_TAG'
    }

    steps {
        shell readFileFromWorkspace('resources/docker_GW_exec.sh')
        rake ('prepare_fixtures') {
        task ('docker:release')
        rake ('docker:tag')
        installation('ruby-2.3.1')
        }
    }

publishers {
            downstreamParameterized {
                trigger('twistlock') {
                    parameters {
                        currentBuild()
                    }
                    condition('SUCCESS')
                }
        }
}
}
