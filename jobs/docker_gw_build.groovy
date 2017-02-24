String basePath = 'Docker Image EXAMPLE'
String repo = 'adobe-apis/api-gateway'

folder(basePath) {
    description 'Docker GW base image'
}

job("$basePath/Docker Image Example") {
    scm {
        github repo
    }
    triggers {
        scm 'H/5 * * * *'
    }
    steps {
        shell readFileFromWorkspace('resources/docker_GW_exec.sh')
        rake ('build') target('test')
        grails {
            useWrapper true
            targets(['test-app', 'war'])
        }
    }
}
