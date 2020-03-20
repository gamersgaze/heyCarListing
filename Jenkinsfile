import groovy.json.JsonSlurper
import hudson.model.*

node('master') {
    def workSpaceHome = pwd()
    stage('Clean') {
        deleteDir()
    }
    stage('Checkout') {
        checkout scm
    }
    stage('Run') {
     
    def foobar = System.getenv("database")
    println(foobar)
    }
    stage('Deploy') {
       

    }
}
