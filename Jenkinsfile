import groovy.json.JsonSlurper

node('master') {
    def workSpaceHome = pwd()
   

    stage('Clean') {
        deleteDir()
    }
    stage('Checkout') {
        checkout scm
    }

    stage('Run') {
       withMaven(jdk: 'JDK', maven: 'maven3', mavenLocalRepo: '', mavenOpts: '', mavenSettingsFilePath: '/opt/qtmserverdependency/settings.xml') {
            def configFile = new File(workSpaceHome+'/config.json')
            def configData = new JsonSlurper().parse(configFile)
            sh "atlas-package"

        }
    }
    stage('Deploy') {
        def inputFile = new File('.//instance.json')
        def inputJSON = new JsonSlurper().parse(inputFile)
        println("deployed succesfully......")
    }
}
