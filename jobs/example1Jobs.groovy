String basePath = 'Deploy Gateway Config - MC STAGE'
String repo = 'git@git.corp.adobe.com:adobe-apis/api-gateway-mc.git'

folder(basePath) {
    description 'Deploy Gateway Config - MC STAGE'
}

job("$basePath/Deploy Gateway Config - MC STAGE") {
    scm {
        github repo
    }
    parameters {
	choiceParam('SIDE', ['Side1 (default)', 'Side2'])
    }
    triggers {
        scm 'H/5 * * * *'
    }
    steps {
        grails {
            useWrapper true
            targets(['test-app', 'war'])
        }
    }
}

job("$basePath/grails example deploy") {
    parameters {
        stringParam 'host'
    }
    steps {
        shell 'scp war file; restart...'
    }
}
