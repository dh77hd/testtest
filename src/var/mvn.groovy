properties([parameters([string(defaultValue: 'test', description: 'test or verify', name: 'testType', trim: false)])])

node {
    stage("git") {
        git url: 'https://github.com/hyunil-shin/java-maven-junit-helloworld.git', branch: "master"
    }
    
    stage('build') {
        withEnv(["PATH+MAVEN=${tool 'mvn-3.3.9'}/bin", "JAVA_HOME=${tool 'openjdk8'}"]) {
            sh "mvn clean ${params.testType}"
        }
    }
    
    stage('report') {
        junit 'target/surefire-reports/*.xml'
        jacoco execPattern: 'target/**.exec'
    }
}
